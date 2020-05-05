package cn.gongyan.learn.controller;

/**
 * @author 龚研
 * @desc ClassesController
 * @date 2020/4/14
 * @qq 1085704190
 **/


import cn.gongyan.learn.beans.entity.Chapter;
import cn.gongyan.learn.beans.entity.Classes;
import cn.gongyan.learn.beans.entity.ClassesInfo;
import cn.gongyan.learn.beans.entity.Theme;
import cn.gongyan.learn.beans.qo.ChapterCreateQo;
import cn.gongyan.learn.beans.qo.ClassCreateQo;
import cn.gongyan.learn.beans.qo.ThemeCreateQo;
import cn.gongyan.learn.beans.vo.ClassesInfoVo;
import cn.gongyan.learn.beans.vo.ResultVO;
import cn.gongyan.learn.service.ChapterService;
import cn.gongyan.learn.service.ClassService;
import cn.gongyan.learn.service.ResourceService;
import cn.gongyan.learn.service.ThemeService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@Api(tags = "Classes APIs")
@RequestMapping("/classes")
public class ClassesController {
    @Autowired
    ClassService classService;
    @Autowired
    ThemeService themeService;
    @Autowired
    ChapterService chapterService;

    /**
     * 除去学生
     * @param id
     * @return
     */
    @GetMapping("/student/delete/{id}")
    @ApiOperation("除去学生")
    public ResultVO<String> deleteStudent(@PathVariable("id") String id){
       // String userId = (String) request.getAttribute("user_id");
        ResultVO<String> resultVO;
        try{
            Integer integer = classService.removeStudent(id);
            resultVO=new ResultVO<>(0,"加入成功",null);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"加入失败",null);
        }
        return resultVO;
    }

    /**
     * 选择课程
     * @param classId
     * @param request
     * @return
     */
    @GetMapping("/joinClass/{id}")
    @ApiOperation("选择课程")
    public ResultVO<String> joinCourse(@PathVariable("id") String classId,HttpServletRequest request){
        String userId = (String) request.getAttribute("user_id");
        ResultVO<String> resultVO;
        try{
            Integer integer = classService.chooseCourse(classId, userId);
            if(integer!=1){
                throw new Exception("选课失败");
            }
            resultVO=new ResultVO<>(0,"加入成功",null);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"加入失败",null);
        }
        return resultVO;
    }

    /**
     * 获得课程预览
     * @param classId
     * @param request
     * @return
     */
    @GetMapping("/preview/{id}")
    @ApiOperation("获得预览")
    public ResultVO<ClassesInfoVo> getClassPreview(@PathVariable("id") String classId,HttpServletRequest request){
        String userId = (String) request.getAttribute("user_id");
        ResultVO<ClassesInfoVo> resultVO;
        try{
            ClassesInfoVo classPreView = classService.getClassPreView(classId);
            resultVO=new ResultVO<>(0,"获取信息成功",classPreView);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"获取失败",null);
        }
        return resultVO;
    }

    /**
     * 添加课程
     * @param createQo
     * @param request
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加课程")
    public ResultVO<Classes> addClasses(@RequestBody  ClassCreateQo createQo,HttpServletRequest request){
        String userId = (String) request.getAttribute("user_id");
        ResultVO<Classes> resultVO;
        try{
            Classes aClass = classService.createClass(createQo, userId);
            resultVO=new ResultVO<>(0,"添加课程成功",aClass);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"添加课程失败",null);
        }
        return resultVO;
    }

    /**
     * 获取学生课程列表
     * @param request
     * @return
     */
    @GetMapping("/student/list")
    @ApiOperation("获取学生课程列表")
    public ResultVO<List<Classes>> getStudentCardList(HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<List<Classes>> resultVO;
        try{
            List<Classes> myClassList = classService.getStudentClassList(userId);
            resultVO=new ResultVO<>(0,"获取课程成功",myClassList);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"获取课程失败",null);
        }
        return resultVO;
    }

    /**
     * 获取学生实训课程列表
     * @param request
     * @return
     */
    @GetMapping("/student/train/list")
    @ApiOperation("获取学生课程列表")
    public ResultVO<List<Classes>> getStudentTrainCardList(HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<List<Classes>> resultVO;
        try{
            List<Classes> myClassList = classService.getStudentTrainClassList(userId);
            resultVO=new ResultVO<>(0,"获取课程成功",myClassList);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"获取课程失败",null);
        }
        return resultVO;
    }

    /**
     * 获取课程列表
     * @param request
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("获取课程列表")
    public ResultVO<List<Classes>> getCardList(HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<List<Classes>> resultVO;
        try{
            List<Classes> myClassList = classService.getMyCreateClassList(userId);
            resultVO=new ResultVO<>(0,"获取课程成功",myClassList);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"获取课程失败",null);
        }
        return resultVO;
    }

    @GetMapping("/train/list")
    @ApiOperation("获取课程列表")
    public ResultVO<List<Classes>> getTrainCardList(HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<List<Classes>> resultVO;
        try{
            List<Classes> myClassList = classService.getMyCreateTrainClassList(userId);
            resultVO=new ResultVO<>(0,"获取实训课程成功",myClassList);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"获取实训课程失败",null);
        }
        return resultVO;
    }

    /**
     * 课堂的具体信息
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/detail/{id}")
    @ApiOperation("获取课程信息")
    public ResultVO<JSONObject> getClassesList(@PathVariable String  id,HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<JSONObject> resultVO;
        try{
            JSONObject classInfo = classService.getClassInfo(id);
            classInfo.put("userId",userId);
            resultVO=new ResultVO<>(0,"获取课程成功",classInfo);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"获取课程失败",null);
        }
        return resultVO;
    }

    @PostMapping("/addTheme")
    @ApiOperation("添加主题")
    public ResultVO<Theme> addTheme(@RequestBody ThemeCreateQo themeCreateQo, HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<Theme> resultVO;
        try{
            Theme theme = themeService.createTheme(themeCreateQo);
            resultVO=new ResultVO<>(0,"添加主题成功",theme);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"添加主题失败",null);
        }
        return resultVO;
    }

    @PostMapping("/addChapter")
    @ApiOperation("添加小节")
    public ResultVO<Chapter> addChapter(@RequestBody ChapterCreateQo createQo, HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<Chapter> resultVO;
        try{
            Chapter chapter = chapterService.createChapter(createQo,userId);
            resultVO=new ResultVO<>(0,"添加小节成功",chapter);
        }catch (Exception e){
            e.printStackTrace();
            resultVO=new ResultVO<>(-1,"添加小节失败",null);
        }
        return resultVO;
    }
}
