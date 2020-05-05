package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.Rigister;
import cn.gongyan.learn.beans.entity.Student;
import cn.gongyan.learn.beans.entity.User;
import cn.gongyan.learn.beans.qo.StudentExcelQo;
import cn.gongyan.learn.beans.vo.StudentVo;
import cn.gongyan.learn.repository.RigisterRepository;
import cn.gongyan.learn.repository.StudentRepository;
import cn.gongyan.learn.repository.UserRepository;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
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
 * @desc StudentService
 * @date 2020/4/17
 * @qq 1085704190
 **/
@Service
@Transactional
public class StudentService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    RigisterRepository rigisterRepository;

    public List<Rigister> getRigisterList(Integer time,Integer number){
        PageRequest pageRequest = PageRequest.of(time, number);
        Page<Rigister> userPage = rigisterRepository.findAll(pageRequest);
        List<Rigister> rigisterList = userPage.getContent();
        return rigisterList;
    }
    /**
     * 在教师管理页面获取学生列表
     * @param time
     * @param number
     * @return
     */
    public List<StudentVo> getStudentList(Integer time,Integer number){
        ArrayList<StudentVo> studentVos = new ArrayList<>();
        User exampleuser = new User();
        exampleuser.setUserRoleId(3);
        exampleuser.setUserEnable(1);
        Example<User> example = null;
        example = Example.of(exampleuser);
        PageRequest pageRequest = PageRequest.of(time, number);
        Page<User> userPage = userRepository.findAll(example,pageRequest);
        List<User> content = userPage.getContent();
        for (User user : content) {
            Student student = new Student();
            student.setSId(user.getUserDataId());
            Student info = studentRepository.findOne(Example.of(student)).orElse(null);
            StudentVo studentVo = new StudentVo();
            studentVo.setAvartar(user.getUserAvatar());
            studentVo.setDesc(user.getUserDescription());
            studentVo.setNickName(user.getUserNickname());
            studentVo.setRealName(info.getUserRealName());
            studentVo.setSchool(info.getUserSchool());
            studentVo.setStudentId(info.getUserStudentId());
            studentVo.setUserId(user.getUserId());
            studentVos.add(studentVo);
        }
        return studentVos;
    }

    /**
     * 根据excel中的一项进行数据添加
     * @param excelQo
     * @return
     */
    public Integer addOneStudent(StudentExcelQo excelQo){
        Student student = new Student();
        User user = new User();
        String userId= IdUtil.simpleUUID();
        String infoId=IdUtil.simpleUUID();

        user.setUserRoleId(3);
        user.setUserPassword(Base64.encode("123"));
        user.setUserDataId(infoId);
        user.setUserEnable(1);
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        user.setUserNickname(excelQo.getName());
        user.setUserUsername(excelQo.getStudentId());
        user.setUserId(userId);
        user.setUserDescription("");
        user.setUserAvatar("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2395693894,1549853405&fm=26&gp=0.jpg");

        student.setUserStudentId(excelQo.getStudentId());
        student.setSId(infoId);
        student.setUserRealName(excelQo.getName());
        student.setUserSchool(excelQo.getSchool());
        student.setUserGrade(excelQo.getGrade());

        User save = userRepository.save(user);
        Student save1 = studentRepository.save(student);
        return (save!=null && save1!=null)?1:0;
    }
}
