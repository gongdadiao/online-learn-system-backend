package cn.gongyan.learn.controller;

import cn.gongyan.learn.beans.entity.Announce;
import cn.gongyan.learn.beans.qo.AnnounceQo;
import cn.gongyan.learn.beans.vo.ResultVO;
import cn.gongyan.learn.service.AnnounceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "Announce APIs")
@RequestMapping("/announce")
public class AnnounceController {
    @Autowired
    AnnounceService announceService;

    @PostMapping("/submit")
    @ApiOperation("上传公告")
    public ResultVO<String> setAnnounce(@RequestBody AnnounceQo announceQo, HttpServletRequest request) {
        String userId = (String) request.getAttribute("user_id");
        ResultVO<String> resultVO;
        try {
            Integer result = announceService.setAnnounce(announceQo, userId);
            if (result == 0)
                throw new Exception("没有添加成功");
            resultVO = new ResultVO<>(0, "添加公告成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "添加公告失败", null);
        }
        return resultVO;
    }

    @GetMapping("/list/{id}")
    @ApiOperation("得到公告")
    public ResultVO<List<Announce>> allAnnounce(@PathVariable String id) {
        ResultVO<List<Announce>> resultVO;
        try {
            List<Announce> announces = announceService.getAllAnnounces(id);
            resultVO = new ResultVO<>(0, "获取公告成功", announces);
        } catch (Exception e) {
            e.printStackTrace();
            resultVO = new ResultVO<>(-1, "获取公告失败", null);
        }
        return resultVO;
    }
}
