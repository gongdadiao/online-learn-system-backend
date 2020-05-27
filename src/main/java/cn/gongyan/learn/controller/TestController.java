package cn.gongyan.learn.controller;

import cn.gongyan.learn.beans.entity.ExamRecord;
import cn.gongyan.learn.beans.entity.Question;
import cn.gongyan.learn.beans.vo.ResultVO;
import cn.gongyan.learn.service.*;
import cn.gongyan.learn.utils.DownLoader;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = " APIs")
@RequestMapping("/test")
public class TestController {
    @Autowired
    ExamService examService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    RecordService recordService;
    @Autowired
    ChapterService chapterService;
    @Autowired
    TestService testService;

    @PostMapping("/submit/{id}")
    @ApiOperation("学生提交作业")
    public ResultVO<String> sumitTest(@PathVariable("id") String id, @RequestBody List<Question> list, HttpServletRequest request){
        ResultVO<String> resultVO;
        String userId = (String)request.getAttribute("user_id");
        try {
            ExamRecord record = examService.addTestRecord(list, userId, id);
            resultVO=new ResultVO<>(0,"批改作业成功",null);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"批改作业失败",null);
        }
        return resultVO;
    }

    @GetMapping("/get/{id}")
    @ApiOperation("获取相应作业")
    public ResultVO<String> getTest(@PathVariable("id") String id, HttpServletRequest request){
        String userId = (String)request.getAttribute("user_id");
        String testId =id;
        String type="homework";
        ResultVO<String> resultVO;
        try {
            ExamRecord record = recordService.getOneRecord(userId, testId, type);
            if(record==null){
                //没有做过这个作业
                ArrayList<Question> list = testService.getQuestionArray(id);
                resultVO=new ResultVO<>(0,"notDone", JSON.toJSONString(list));
            }else{
                //做过这个作业
                String download = DownLoader.download(record.getExamOptionUrl());
                System.out.println(download);
                resultVO=new ResultVO<>(1,"hasDone",download);
            }
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"获取作业错误",null);
        }
        return resultVO;
    }


}