/***********************************************************
 * @Description : 考试控制器
 * @author      : 龚研
 * @date        : 2020-03-31 08:04
 * @qq          : 1085704190
 ***********************************************************/
package cn.gongyan.learn.controller;

import cn.gongyan.learn.beans.entity.Exam;
import cn.gongyan.learn.beans.entity.ExamRecord;
import cn.gongyan.learn.beans.entity.Question;
import cn.gongyan.learn.beans.jsonobject.OneExam;
import cn.gongyan.learn.beans.jsonobject.OneQuestion;
import cn.gongyan.learn.beans.qo.ResourceQo;
import cn.gongyan.learn.beans.vo.ExamDetailVo;
import cn.gongyan.learn.beans.vo.OneExamVo;
import cn.gongyan.learn.beans.vo.QuestionPageVo;
import cn.gongyan.learn.beans.vo.ResultVO;
import cn.gongyan.learn.service.ExamService;
import cn.gongyan.learn.service.QuestionService;
import cn.gongyan.learn.service.RecordService;
import cn.gongyan.learn.service.ResourceService;
import cn.gongyan.learn.utils.CommonValues;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "Exam APIs")
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    QuestionService questionService;
    @Autowired
    ExamService examService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    RecordService recordService;

    /**
     * 完成考试
     * @param id    考试id
     * @param answerPage 用户提交的答卷
     * @param request
     * @return
     */
    @PostMapping("/finish/{id}")
    @ApiOperation("用户 提交答案")
    ResultVO<ExamRecord> submit(@PathVariable String id,@RequestBody String answerPage,HttpServletRequest request){
        ResultVO<ExamRecord> resultVO=new ResultVO();
        String userId=(String)request.getAttribute("user_id");
        OneExamVo exam = JSONObject.parseObject(answerPage, OneExamVo.class);
        try {
            ExamRecord examRecord = examService.addExamRecord(exam, userId,id);
            resultVO=new ResultVO<>(0,"成功交卷",examRecord);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"交卷出现错误",null);
        }
        return resultVO;
    }

    /**
     * 学生参加考试
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/detail/{id}")
    @ApiOperation("学生 获取考试，需要进行保密处理")
    ResultVO<ExamDetailVo> getExamDetail(@PathVariable String id, HttpServletRequest request) {
        String userId=(String)request.getAttribute("user_id");
        ResultVO<ExamDetailVo> resultVO;
        //判断是否已经参加过考试
        ExamRecord exam = recordService.getOneRecord(userId, id, "exam");
        if(exam!=null) {
            //已经参加过考试
            if (exam.getExamStatus().compareTo(CommonValues.examStatusComplete) == 0) {
                //已经批改完成
                ExamDetailVo detail = examService.getResultDetail(exam);
                resultVO = new ResultVO<>(1, CommonValues.examStatusComplete, detail);
            } else if (exam.getExamStatus().compareTo(CommonValues.examStatusWaitToBeMarked) == 0) {
                //等待批改
                resultVO = new ResultVO<>(-2, CommonValues.examStatusWaitToBeMarked, null);
            } else {
                resultVO = new ResultVO<>(-3, CommonValues.examStatusBaijuan, null);
            }
            return resultVO;
        }
        // 根据id获取考试详情

        try {
            ExamDetailVo detail = examService.getExamDetail(id,userId);
            resultVO = new ResultVO<>(0, "获取考试详情成功", detail);
        }catch (Exception e){
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获取考试详情失败", null);
        }
        return resultVO;
    }
    /**
     *
     * @param text  老师创建的考试
     * @param request
     * @return
     */
    @PostMapping("/create")
    @ApiOperation("老师 创建考试")
    ResultVO<Exam> createExam(@RequestBody String text, HttpServletRequest request){
        String userId = (String) request.getAttribute("user_id");
        ResultVO<Exam> resultVO;
        OneExam exam = JSONObject.parseObject(text, OneExam.class);
        String id = (String)request.getAttribute("user_id");
        try{
            OneExam examInfo = JSONObject.parseObject(text, OneExam.class);
            ResourceQo resourceQo = new ResourceQo();
            resourceQo.setResAvatar(examInfo.getExamAvatar());
            resourceQo.setResDescription(examInfo.getExamDescription());
            resourceQo.setResName(examInfo.getExamName());
            resourceQo.setResType("考试");
            resourceQo.setResUrl(null);
            Exam examEntity = examService.createExam(exam, id);
            resourceQo.setExamId(examEntity.getExamId());
            Integer result = resourceService.addOneResource(resourceQo, userId);
            if(result==0){
                throw new Exception("添加资源失败");
            }
            resultVO = new ResultVO<>(0, "成功添加考试",examEntity);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"创建考试失败", null);
        }
        return resultVO;
    }
    /**
     * 获取一定考试题目数量
     * @param number
     * @return
     */
    @GetMapping("/question/list/{number}")
    @ApiOperation("老师 创建考试时,获取定量题目")
    ResultVO<List<Question>> getQuestionList(@PathVariable("number")Integer number){
        ResultVO<List<Question>> resultVO;
        try {
            resultVO=new ResultVO<>(0,"成功获取题目",questionService.getQuestions(number));
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"获取题目失败",null);
        }
        return resultVO;
    }
    /**
     * 获取题目库显示的页面
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/question/pageList")
    @ApiOperation("获取定量页面题目")
    ResultVO<QuestionPageVo> getPageQuestionList(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        ResultVO<QuestionPageVo> resultVO;
        try {
            QuestionPageVo page = questionService.getQuestionsAsPage(pageNo, pageSize);
            resultVO=new ResultVO<>(0,"成功获取题目",page);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"获取题目失败",null);
        }
        return resultVO;
    }
    /**
     * 创建题目
     * @param text
     * @param request
     * @return
     */
    @PostMapping("/question/create")
    @ApiOperation("老师 创建题目")
    ResultVO<Question> createQuestion(@RequestBody String text, HttpServletRequest request){
        ResultVO<Question> resultVO;
        try {
            OneQuestion oneQuestion = JSON.parseObject(text, OneQuestion.class);
            Integer success = questionService.createOneQuestion(oneQuestion);
            if(success==0){
                throw new Exception("创建题目失败");
            }
            resultVO=new ResultVO<>(0,"创建题目成功",null);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"创建题目失败",null);
        }
        return resultVO;
    }
}
