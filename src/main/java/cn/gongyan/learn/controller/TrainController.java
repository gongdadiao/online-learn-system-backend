package cn.gongyan.learn.controller;

import cn.gongyan.learn.beans.entity.TrainningQuestion;
import cn.gongyan.learn.beans.qo.CodeRunQo;
import cn.gongyan.learn.beans.qo.TrainCourseQo;
import cn.gongyan.learn.beans.qo.TrainQuestionCreateQo;
import cn.gongyan.learn.beans.vo.ResultVO;
import cn.gongyan.learn.beans.vo.TrainCourseDetailVo;
import cn.gongyan.learn.beans.vo.TrainQuestionPageVo;
import cn.gongyan.learn.beans.vo.TrainQuestionVo;
import cn.gongyan.learn.common.CheckPair;
import cn.gongyan.learn.repository.TrainQuestionRepository;
import cn.gongyan.learn.service.CodeService;
import cn.gongyan.learn.service.TrainCourseService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tools.ant.filters.StringInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "train APIs")
@RequestMapping("/train")
public class TrainController {
    @Autowired
    TrainCourseService trainCourseService;
    @Autowired
    CodeService codeService;
    @Autowired
    TrainQuestionRepository trainQuestionRepository;

    @PostMapping("/code/run")
    @ApiOperation("运行用户提交的代码")
    public ResultVO<String> runCode(@RequestBody CodeRunQo codeRunQo){
        ResultVO<String> resultVO;
        try {
            String code = trainCourseService.runCode(codeRunQo);
            if(code.compareTo("成功")==0){
                resultVO=new ResultVO(0,"运行成功 ",code);
            }else{
                resultVO=new ResultVO(-1,"运行不成功 ",code);
            }
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"运行代码失败","运行失败");
        }
        return resultVO;
    }
    /**
     * 获取学生课程的题目
     * @param classId
     * @param request
     * @return
     */
    @GetMapping("/course/studentdetail/{classId}")
    @ApiOperation("获得题目列表")
    public ResultVO<List<TrainQuestionVo>> StudentTrainCourseDetail(@PathVariable String classId, HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<List<TrainQuestionVo>> resultVO;
        try {
            List<TrainQuestionVo> questions = trainCourseService.getCourseQuestionsForStudent(classId);
            resultVO = new ResultVO<>(0, "获得题目列表成功", questions);
        }catch (Exception e){
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获得题目列表失败", null);
        }
        return resultVO;
    }

    @PostMapping("/create/check/{questionId}")
    @ApiOperation("创建一个实训题目的审核")
    public ResultVO<List<String>> createCheck(@PathVariable("questionId") Integer id, @RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<List<String>> resultVO;
        try {
            ArrayList<String> resultList = new ArrayList<>();
            TrainningQuestion question = trainQuestionRepository.findById(id).orElse(null);
            ArrayList<CheckPair> checkPairs = new ArrayList<>();
            for (MultipartFile file : files) {
                CheckPair pair = new CheckPair();
                String in = new String(file.getBytes());
                pair.setInput(in);
                InputStream inputStream = new StringInputStream(in);
                System.setIn(inputStream);
                String execute = codeService.execute(question.getQRealAnswer());
                if(execute.length()<=20){
                    resultList.add(execute);
                }else{
                    resultList.add(execute.substring(0,20));
                }
                System.setIn(System.in);
                System.in.reset();
                pair.setOutput(execute);
                checkPairs.add(pair);
            }
            Integer integer = trainCourseService.setCheckForQuestion(id, checkPairs);
            resultVO = new ResultVO<>(0, "创建实训测试成功", resultList);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "创建实训测试失败", null);
        }
        return resultVO;
    }

    /**
     * 创建实训题目
     * @param questionQo
     * @param request
     * @return
     */
    @PostMapping("/question/create")
    @ApiOperation("创建一个实训题目")
    public ResultVO<Integer> createQuestion(@RequestBody TrainQuestionCreateQo questionQo, HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<Integer> resultVO;
        try {
            TrainningQuestion trainingQuestion = trainCourseService.createTrainingQuestion(questionQo);
            resultVO = new ResultVO<>(0, "创建实训题目成功", trainingQuestion.getQId());
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "创建实训题目失败", null);
        }
        return resultVO;
    }

    /**
     * 获取课程详情
     * @param classId
     * @param request
     * @return
     */
    @GetMapping("/course/detail/{classId}")
    @ApiOperation("获取课程细节详情")
    public ResultVO<TrainCourseDetailVo> courseDetail(@PathVariable String classId, HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<TrainCourseDetailVo> resultVO;
        try {
            TrainCourseDetailVo detail = trainCourseService.getCourseDetail(classId);
            resultVO = new ResultVO<>(0, "获取课程详情成功", detail);
        }catch (Exception e){
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获取课程详情失败", null);
        }
        return resultVO;
    }

    /**
     * 获取题目列表
     * @param request
     * @return
     */
    @GetMapping("/questions/list")
    @ApiOperation("获得题目列表")
    public ResultVO<List<TrainningQuestion>> listQuestions(HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<List<TrainningQuestion>> resultVO;
        try {
            List<TrainningQuestion> trainningQuestions = trainCourseService.questionList();
            resultVO = new ResultVO<>(0, "获得题目列表成功", trainningQuestions);
        }catch (Exception e){
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获得题目列表失败", null);
        }
        return resultVO;
    }
    /**
     * 获取题目列表
     * @param request
     * @return
     */
    @GetMapping("/questions/page")
    @ApiOperation("获得题目页面")
    public ResultVO<TrainQuestionPageVo> pageQuestions(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<TrainQuestionPageVo> resultVO;
        try {
            TrainQuestionPageVo pageList = trainCourseService.questionPageList(pageNo, pageSize);
            resultVO = new ResultVO<>(0, "获得题目列表成功", pageList);
        }catch (Exception e){
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获得题目列表失败", null);
        }
        return resultVO;
    }
//    /**
//     * 添加题目
//     * @param request
//     * @return
//     */
//    @GetMapping("/questions/create")
//    @ApiOperation("创建题目")
//    public ResultVO<Integer> createTrainQuestion(@RequestBody TrainQuestionQo trainQuestionQo,HttpServletRequest request) {
//        String userId = (String) request.getAttribute("user_id");
//        ResultVO<Integer> resultVO;
//        try {
//            TrainningQuestion question = trainCourseService.createTrainingQuestion(trainQuestionQo);
//            resultVO = new ResultVO<>(0, "创建题目成功", question.getQId());
//        }catch (Exception e){
//            e.printStackTrace();
//            resultVO = new ResultVO<>(-1, "创建题目失败", null);
//        }
//        return resultVO;
//    }
    /**
     * 为课程设置题目
     * @param classId
     * @param ids
     * @param request
     * @return
     */
    @PostMapping("/select/questions/{classId}")
    @ApiOperation("实训课程选择题目")
    public ResultVO<Integer> selectQuestions(@PathVariable String classId,@RequestBody List<Integer> ids,HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<Integer> resultVO;
        try {
            Integer integer = trainCourseService.selectQuestionsForCourse(ids, classId);
            if(integer==0)
                throw new Exception("添加题目列表失败");
            resultVO = new ResultVO<>(0, "创建实训课程题目成功", integer);
        }catch (Exception e){
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "创建实训课程题目失败", null);
        }
        return resultVO;
    }
    /**
     * 创建一个课程
     * @param trainCourseQo
     * @param request
     * @return
     */
//    @PostMapping("/create/course")
//    @ApiOperation("创建一个实训课程")
//    public ResultVO<Integer> createCourse(TrainCourseQo trainCourseQo, HttpServletRequest request) {
//        String userId = (String) request.getAttribute("user_id");
//        ResultVO<Integer> resultVO;
//        try {
//            Integer trainCourse = trainCourseService.createTrainCourse(trainCourseQo, userId);
//            resultVO = new ResultVO<>(0, "创建实训课程成功", trainCourse);
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultVO = new ResultVO<>(-1, "创建实训课程失败", null);
//        }
//        return resultVO;
//    }
}