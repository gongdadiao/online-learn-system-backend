package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.*;
import cn.gongyan.learn.beans.qo.CodeRunQo;
import cn.gongyan.learn.beans.qo.TrainCourseQo;
import cn.gongyan.learn.beans.qo.TrainQuestionCreateQo;
import cn.gongyan.learn.beans.vo.TrainCourseDetailVo;
import cn.gongyan.learn.beans.vo.TrainQuestionPageVo;
import cn.gongyan.learn.beans.vo.TrainQuestionVo;
import cn.gongyan.learn.common.CheckPair;
import cn.gongyan.learn.repository.*;
import cn.gongyan.learn.utils.DownLoader;
import cn.gongyan.learn.utils.UpLoader;
import cn.hutool.core.util.IdUtil;
import org.apache.tools.ant.filters.StringInputStream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Max;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 龚研
 * @desc TrainCourseService
 * @date 2020/4/24
 * @qq 1085704190
 **/
@Service
@Transactional
public class TrainCourseService {
    @Autowired
    TrainCheckRepository trainCheckRepository;
    @Autowired
    TrainQuestionRepository trainQuestionRepository;
    @Autowired
    CourseQuestionRepository courseQuestionRepository;
    @Autowired
    ClassesRepository classesRepository;
    @Autowired
    CodeService codeService;

    public String runCode(CodeRunQo codeRunQo){
        TrainningCheck check = new TrainningCheck();
        check.setQuestionId(codeRunQo.getQuestionId());
        List<TrainningCheck> checkList = trainCheckRepository.findAll(Example.of(check));
        for (TrainningCheck checkItem : checkList) {
            String in= DownLoader.download(checkItem.getAnswerUrl());
            InputStream inputStream = new StringInputStream(in);
            System.setIn(inputStream);
            String execute = codeService.execute(codeRunQo.getCode());
            System.setIn(System.in);
            try {
                System.in.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String answer = DownLoader.download(checkItem.getAnswerUrl());
            if(execute.compareTo(answer+"\r\n")!=0){
                return execute;
            }
        }
        return "成功";
    }
    /**
     * 获取学生看到的题目列表
     * @param classId
     * @return
     */
    public List<TrainQuestionVo> getCourseQuestionsForStudent(String classId){
        ArrayList<TrainQuestionVo> trainQuestionVos = new ArrayList<>();
        QuestionCourse questionCourse = new QuestionCourse();
        questionCourse.setCourseId(classId);
        List<QuestionCourse> ids = courseQuestionRepository.findAll(Example.of(questionCourse));
        for (QuestionCourse id : ids) {
            TrainningQuestion question = trainQuestionRepository.findById(id.getQuestionId()).orElse(null);
            TrainQuestionVo trainQuestionVo = new TrainQuestionVo();
            trainQuestionVo.setTitle(question.getQTitle());
            trainQuestionVo.setContent(question.getQContent());
            trainQuestionVo.setInicode(question.getQInitialCode());
            trainQuestionVo.setId(question.getQId());
            trainQuestionVos.add(trainQuestionVo);
        }
        return trainQuestionVos;
    }
    /**
     * 创建一个课程
     * @param courseQo
     * @param userId
     * @return
     */
//    public Integer createTrainCourse(TrainCourseQo courseQo,String userId){
//        TrainningCourse course = new TrainningCourse();
//        course.setTId(IdUtil.simpleUUID());
//        BeanUtils.copyProperties(courseQo,course);
//        course.setCreateTime(LocalDateTime.now());
//        course.setTCreatorId(userId);
//
//        TrainningCourse save = trainCourseRepository.save(course);
//        return (save==null)?0:1;
//    }

    /**
     * 创建一个实训题目
     * @param questionQo
     * @return
     */
    public TrainningQuestion createTrainingQuestion(TrainQuestionCreateQo questionQo) {
        TrainningQuestion question = new TrainningQuestion();
        question.setQContent(questionQo.getContent());
        question.setQDesc(questionQo.getDesc());
        question.setQInitialCode(questionQo.getCode());
        question.setQRealAnswer(questionQo.getAns());
        question.setQTitle(questionQo.getTitle());
        question.setQId(0);
        TrainningQuestion saveQuestion = trainQuestionRepository.save(question);
        return saveQuestion;
    }

    /**
     * 设置所有check
     * @param questionId
     * @param pairs
     * @return
     */
    public Integer setCheckForQuestion(Integer questionId,List<CheckPair> pairs){
        for (CheckPair p : pairs) {
            TrainningCheck check = new TrainningCheck();
            check.setQuestionId(questionId);
            String iurl = UpLoader.uploadQiniuText("check", p.getInput(), String.valueOf(System.currentTimeMillis()));
            String ourl = UpLoader.uploadQiniuText("check", p.getOutput(), String.valueOf(System.currentTimeMillis()+100));
            check.setAnswerUrl(ourl);
            check.setInputUrl(iurl);
            trainCheckRepository.save(check);
        }
        return 1;
    }
    /**
     * 选择实训题目
     * @param ids
     * @param courseId
     * @return
     */
    public Integer chooseQuestions(List<Integer> ids,String courseId){
        for (Integer id : ids) {
            QuestionCourse questionCourse = new QuestionCourse();
            questionCourse.setCourseId(courseId);
            questionCourse.setQuestionId(id);
            questionCourse.setId(0);
            courseQuestionRepository.save(questionCourse);
        }
        return 1;
    }

    /**
     * 选择课程的题目
     * @param questionIds
     * @param classId
     * @return
     */
    public Integer selectQuestionsForCourse(List<Integer> questionIds,String classId){
        for (Integer id : questionIds) {
            QuestionCourse questionCourse = new QuestionCourse();
            questionCourse.setCourseId(classId);
            questionCourse.setQuestionId(id);
            QuestionCourse save = courseQuestionRepository.save(questionCourse);
            if(save==null)
                return 0;
        }
        return 1;
    }

    /**
     * 获得所有题目列表
     * @return
     */
    public List<TrainningQuestion> questionList(){
        List<TrainningQuestion> list = trainQuestionRepository.findAll();
        return list;
    }

    /**
     * 获得题目页面
     * @param pageNo
     * @param pageSize
     * @return
     */
    public TrainQuestionPageVo questionPageList(Integer pageNo, Integer pageSize){
        // 按照日期降序排列
        //Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        // 构造分页请求,注意前端面页面的分页是从1开始的，后端是从0开始地，所以要减去1
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        Page<TrainningQuestion> questionPage = trainQuestionRepository.findAll(pageRequest);
        TrainQuestionPageVo trainQuestionPageVo = new TrainQuestionPageVo();
        // 设置页码
        trainQuestionPageVo.setPageNo(pageNo);
        // 设置每页有多少条数据
        trainQuestionPageVo.setPageSize(pageSize);
        // 设置总共有多少个元素
        trainQuestionPageVo.setTotalCount(questionPage.getTotalElements());
        // 设置一共有多少页
        trainQuestionPageVo.setTotalPage(questionPage.getTotalPages());
        trainQuestionPageVo.setQuestionVoList(questionPage.getContent());
        return trainQuestionPageVo;
    }
    /**
     * 获取课程信息
     * @param classId
     * @return
     */
    public TrainCourseDetailVo getCourseDetail(String classId){
        TrainCourseDetailVo detailVo = new TrainCourseDetailVo();
        Classes classes = classesRepository.findById(classId).orElse(null);
        QuestionCourse questionCourse = new QuestionCourse();
        questionCourse.setCourseId(classId);
        List<QuestionCourse> ids = courseQuestionRepository.findAll(Example.of(questionCourse));
        ArrayList<TrainningQuestion> questions = new ArrayList<>();
        for (QuestionCourse id : ids) {
            TrainningQuestion question = trainQuestionRepository.findById(id.getQuestionId()).orElse(null);
            questions.add(question);
        }
        detailVo.setAvartar(classes.getCAvartar());
        detailVo.setDesc(classes.getCDescription());
        detailVo.setTitle(classes.getCTitle());
        detailVo.setQuestionList(questions);

        return detailVo;
    }
}
