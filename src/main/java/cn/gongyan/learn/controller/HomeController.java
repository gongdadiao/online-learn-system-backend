package cn.gongyan.learn.controller;

import cn.gongyan.learn.beans.jsonobject.home.HomeNavData;
import cn.gongyan.learn.beans.vo.ResultVO;
import cn.gongyan.learn.service.HomeService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = " APIs")
@RequestMapping("/view")
public class HomeController {
    @Autowired
    HomeService homeService;

    @GetMapping("/nav")
    @ApiOperation("设置导航栏目")
    public ResultVO<List<HomeNavData>> setNavViews() {
        //String userId = (String) request.getAttribute("user_id");
        ResultVO<List<HomeNavData>> resultVO;
        try {
            List<HomeNavData> navList = homeService.getHomeNavList();
            resultVO = new ResultVO<>(0, "成功", navList);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "失败", null);
        }
        return resultVO;
    }


    @GetMapping("/recommend")
    @ApiOperation("设置推荐栏目")
    public ResultVO<JSONObject> setRecViews() {
        //String userId = (String) request.getAttribute("user_id");
        ResultVO<JSONObject> resultVO;
        try {
            JSONObject courses = homeService.getCourses();
            resultVO = new ResultVO<>(0, "成功", courses);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "失败", null);
        }
        return resultVO;
    }

}