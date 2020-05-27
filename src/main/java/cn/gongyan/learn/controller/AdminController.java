package cn.gongyan.learn.controller;

import cn.gongyan.learn.beans.vo.ResultVO;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = " APIs")
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/setInfo")
    @ApiOperation("")
    public ResultVO<String> xxxxxx(HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<String> resultVO;
        try {
            resultVO = new ResultVO<>(0, "成功",null );
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "失败", null);
        }
        return resultVO;
    }
}