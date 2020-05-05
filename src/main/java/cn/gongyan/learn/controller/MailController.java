package cn.gongyan.learn.controller;

import cn.gongyan.learn.beans.entity.Mail;
import cn.gongyan.learn.beans.qo.MailQo;
import cn.gongyan.learn.beans.vo.MailVo;
import cn.gongyan.learn.beans.vo.ResultVO;
import cn.gongyan.learn.service.MailService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "Mail APIs")
@RequestMapping("/mail")
public class MailController {
    @Autowired
    MailService mailService;

    /**
     * 用户发送信息
     * @param mailQo
     * @param request
     * @return
     */
    @PostMapping("/send")
    @ApiOperation("发送信息")
    public ResultVO<String> sendMsg(@RequestBody MailQo mailQo, HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<String> resultVO;
        try {
            Integer integer = mailService.sendOneMessage(mailQo);
            if(integer!=1){
                throw new Exception("发送出错");
            }
            resultVO = new ResultVO<>(0, "成功", null );
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "失败", null);
        }
        return resultVO;
    }

    /**
     * 获取用户的信息
     * @param request
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("发送信息")
    public ResultVO<List<MailVo>> getMsgs(HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<List<MailVo>> resultVO;
        try {
            List<MailVo> messages = mailService.getMessages(userId);
            resultVO = new ResultVO<>(0, "成功", messages );
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "失败", null);
        }
        return resultVO;
    }
}