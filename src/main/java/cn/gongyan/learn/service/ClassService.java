package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.*;
import cn.gongyan.learn.beans.qo.ClassCreateQo;
import cn.gongyan.learn.beans.vo.ClassPageVo;
import cn.gongyan.learn.beans.vo.ClassesInfoVo;
import cn.gongyan.learn.beans.vo.StudentVo;
import cn.gongyan.learn.repository.*;
import cn.gongyan.learn.utils.CommonValues;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 龚研
 * @desc ClassService
 * @date 2020/4/12
 * @qq 1085704190
 **/

@Service
@Transactional
public class ClassService {
    @Autowired
    ClassesRepository classesRepository;
    @Autowired
    ThemeRepository themeRepository;
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseStudentRepository courseStudentRepository;
    @Autowired
    ClassesInfoRepository classesInfoRepository;
    @Autowired
    StudentRepository studentRepository;

    /**
     * 除去选这么课的学生
     * @param studentId
     * @return
     */
    public Integer removeStudent(String studentId){
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCsStudentId(studentId);
        CourseStudent student = courseStudentRepository.findOne(Example.of(courseStudent)).orElse(null);
        courseStudentRepository.deleteById(student.getCsId());
        return 1;
    }

    public Integer chooseCourse(String classId,String studentId){
        //确认是否为学生
        if(userRepository.findById(studentId).orElse(null).getUserRoleId()!=3){
            return -1;
        }
        CourseStudent example = new CourseStudent();
        example.setCsStudentId(studentId);
        example.setCsCourseId(classId);
        CourseStudent one = courseStudentRepository.findOne(Example.of(example)).orElse(null);
        if(one!=null)
            return -1;
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCsId(0);
        courseStudent.setCsStudentId(studentId);
        courseStudent.setCsCourseId(classId);
        courseStudent.setCreateTime(new Date());
        CourseStudent save = courseStudentRepository.save(courseStudent);
        return (save!=null)?1:0;
    }

    /**
     * 获取课程概述
     * @param classId
     * @return
     */
    public ClassesInfoVo getClassPreView(String classId){
        ClassesInfoVo classesInfoVo=new ClassesInfoVo();
        //设置课程详细信息
        ClassesInfo info = new ClassesInfo();
        info.setClassesId(classId);
        ClassesInfo classesInfo = classesInfoRepository.findOne(Example.of(info)).orElse(null);
        if(classesInfo!=null)
            BeanUtils.copyProperties(classesInfo,classesInfoVo);
        //设置课程基本信息
        Classes classes = classesRepository.findById(classId).orElse(null);
        classesInfoVo.setClassTitle(classes.getCTitle());
        classesInfoVo.setClassAvatar(classes.getCAvartar());
        classesInfoVo.setClassDesc(classes.getCDescription());
        classesInfoVo.setClassType(classes.getCType());
        //设置教师信息
        User user = userRepository.findById(classes.getCCreatorId()).orElse(null);
        classesInfoVo.setTeacherName(user.getUserNickname());
        classesInfoVo.setTeacherAvatar(user.getUserAvatar());
        classesInfoVo.setTeacherDesc(user.getUserDescription());

        return classesInfoVo;
    }
    /**
     * 创建课程
     * @param createQo
     * @return
     */
    public Classes createClass(ClassCreateQo createQo,String userId){
       try{
           Classes classes = new Classes();
           classes.setCId(IdUtil.simpleUUID());
           classes.setCAvartar(createQo.getAvartar());
           classes.setCreateTime(new Date());
           classes.setStartTime(createQo.getStartTime());
           classes.setEndTime(createQo.getEndTime());
           classes.setCCreatorId(userId);
           classes.setCTerm(createQo.getTerm());
           classes.setCEnable(1L);
           classes.setCTitle(createQo.getTitle());
           classes.setCDescription(createQo.getDesc());
           classes.setCThemeId(createQo.getType());
           classes.setCType(createQo.getClassType());

           classesRepository.save(classes);
           return classes;
       }catch (Exception e){
           e.printStackTrace();
       }
       return null;
    }

    /**
     * 获取一个学生选择得所有课程
     * @param userId
     * @return
     */
    public List<Classes> getStudentClassList(String userId){
        List<Classes> classesList = new ArrayList<>();
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCsStudentId(userId);
        List<CourseStudent> classIdList = courseStudentRepository.findAll(Example.of(courseStudent));
        for (CourseStudent id : classIdList) {
            Classes oneclass = classesRepository.findById(id.getCsCourseId()).orElse(null);
            if(oneclass.getCType().compareTo(CommonValues.classType_train)==0)
                continue;
            classesList.add(oneclass);
        }
        return classesList;
    }

    /**
     * 获取一个学生选择得所有实训课程
     * @param userId
     * @return
     */
    public List<Classes> getStudentTrainClassList(String userId){
        List<Classes> classesList = new ArrayList<>();
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCsStudentId(userId);
        List<CourseStudent> classIdList = courseStudentRepository.findAll(Example.of(courseStudent));
        for (CourseStudent id : classIdList) {
            Classes oneclass = classesRepository.findById(id.getCsCourseId()).orElse(null);
            if(oneclass.getCType().compareTo(CommonValues.classType_common)==0)
                continue;
            classesList.add(oneclass);
        }
        return classesList;
    }

    /**
     * 获取选择这门课得所有学生
     * @param classId
     * @return
     */
    public List<StudentVo> getCourseStudentsList(String classId){
        List<StudentVo> studentsList = new ArrayList<>();
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCsCourseId(classId);
        List<CourseStudent> courseStudents = courseStudentRepository.findAll(Example.of(courseStudent));
        for (CourseStudent id : courseStudents) {
            User user = userRepository.findById(id.getCsStudentId()).orElse(null);
            Student student = studentRepository.findById(user.getUserDataId()).orElse(null);
            StudentVo studentVo = new StudentVo();
            studentVo.setUserId(user.getUserId());
            studentVo.setStudentId(student.getUserStudentId());
            studentVo.setSchool(student.getUserSchool());
            studentVo.setRealName(student.getUserRealName());
            studentVo.setNickName(user.getUserNickname());
            studentVo.setDesc(user.getUserDescription());
            studentVo.setAvartar(user.getUserAvatar());

            studentsList.add(studentVo);
        }
        return studentsList;
    }
    /**
     * 获取我创建的课程
     * @param userId
     * @return
     */
    public List<Classes> getMyCreateClassList(String userId){
        Classes classes = new Classes();
        classes.setCCreatorId(userId);
        classes.setCType(CommonValues.classType_common);
        List<Classes> all = classesRepository.findAll(Example.of(classes));
        return all;
    }
    /**
     * 获取我创建的实训课程
     * @param userId
     * @return
     */
    public List<Classes> getMyCreateTrainClassList(String userId){
        Classes classes = new Classes();
        classes.setCCreatorId(userId);
        classes.setCType(CommonValues.classType_train);
        List<Classes> all = classesRepository.findAll(Example.of(classes));
        return all;
    }
    /**
     * 测试函数，获取所选课程
     * @return
     */
    public List<Classes> getMyClassList(String userId){
        ArrayList<Classes> list = new ArrayList<>();
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCsStudentId(userId);
        List<CourseStudent> allcourse = courseStudentRepository.findAll(Example.of(courseStudent));
        for (CourseStudent courseId : allcourse) {
            Classes classes = classesRepository.findById(courseId.getCsCourseId()).orElse(null);
            list.add(classes);
        }
        return list;
    }

    /**
     * 获取我的课程列表,按照页数获取
     * @param pageNo
     * @param pageSize
     * @return
     */
    public ClassPageVo getMyClassesListAsPage(Integer pageNo, Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        Page<Classes> classPage = classesRepository.findAll(pageRequest);
        ClassPageVo classPageVo = new ClassPageVo();
        // 设置页码
        classPageVo.setPageNo(pageNo);
        // 设置每页有多少条数据
        classPageVo.setPageSize(pageSize);
        // 设置总共有多少个元素
        classPageVo.setTotalCount(classPage.getTotalElements());
        // 设置一共有多少页
        classPageVo.setTotalPage(classPage.getTotalPages());
        // 取出当前页的考试列表
        List<Classes> classList = classPage.getContent();
        classPageVo.setClassVoList(classList);
        return classPageVo;
    }

    /**
     * 获取课堂具体信息
     * @param classId
     * @return
     */
    public JSONObject getClassInfo(String classId) {
        Classes classInfo = classesRepository.findById(classId).orElse(null);
        JSONObject object = new JSONObject();
        Theme theme = new Theme();
        theme.setThemeClassId(classId);
        List<Theme> list = themeRepository.findAll(Example.of(theme));

        JSONObject base = new JSONObject();
        base.put("title",classInfo.getCTitle());
        base.put("rank","大学生");
        base.put("duration", classInfo.getStartTime().toString()+"-"+classInfo.getEndTime().toString());
        base.put("number",0);
        base.put("praise", 0);
        base.put("isComplete", true);

        JSONObject teacher = new JSONObject();
        User user = userRepository.findById(classInfo.getCCreatorId()).orElse(null);
        teacher.put("avatar",user.getUserAvatar());
        teacher.put("name",user.getUserNickname());
        teacher.put("job","老师");
        teacher.put("introduce",user.getUserDescription());
        teacher.put("teacherId",user.getUserId());

        JSONObject chapter = new JSONObject();
        chapter.put("introduce","");
        JSONArray chapter_data = new JSONArray();
        for (Theme item : list) {
            JSONObject chapter_data_item = new JSONObject();
            chapter_data_item.put("title",item.getThemeName());
            chapter_data_item.put("id",item.getThemeId());
            JSONArray chapter_data_item_term = new JSONArray();
            Integer index=1;

            Chapter chap = new Chapter();
            chap.setChThemeId(item.getThemeId());
            List<Chapter> chaplist = chapterRepository.findAll(Example.of(chap));
            for (Chapter res : chaplist) {
                JSONObject term = new JSONObject();
                term.put("id",res.getChId());
               // term.put("avatar",res.getResAvatar());
                term.put("type",res.getChType());
               // term.put("url",res.getgetResUrl());
                term.put("title",res.getChTitle());
                term.put("rate",index);
                index++;
                chapter_data_item_term.add(term);
            }
            chapter_data_item.put("term",chapter_data_item_term);
            chapter_data.add(chapter_data_item);
        }
        chapter.put("data",chapter_data);

        object.put("base",base);
        object.put("teacher",teacher);
        object.put("chapter",chapter);

        return object;
    }
}
