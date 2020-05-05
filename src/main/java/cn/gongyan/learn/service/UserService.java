/***********************************************************
 * @Description : 用户接口
 * @author      : 龚研
 * @date        : 2019-05-17 07:43
 * @qq          : 1085704190
 ***********************************************************/
package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.Rigister;
import cn.gongyan.learn.beans.entity.User;
import cn.gongyan.learn.beans.qo.LoginQo;
import cn.gongyan.learn.beans.qo.RegisterQo;
import cn.gongyan.learn.beans.qo.SettingQo;
import cn.gongyan.learn.beans.vo.RigisterPageVo;
import cn.gongyan.learn.beans.vo.UserInfoVo;
import cn.gongyan.learn.beans.vo.UserVo;

import java.util.List;

public interface UserService {
    Boolean anthCheck(String userId);
    /**
     * 注册申请，等候管理员亲自认证
     * @param registerDTO 注册参数
     * @return 注册成功后的用户信息
     */
    Rigister register(RegisterQo registerDTO);
    /**
     * 列举所有申请的学生
     * @return
     */
    RigisterPageVo listRegRequest(Integer pageNo, Integer pageSize);
    /**
     * 认证用户为学生，登陆
     * @param rigisterId
     * @return
     */
    User authenUser(String rigisterId);
    /**
     * 登录接口，登录成功返回token
     *
     * @param loginQo 登录参数
     * @return 成功返回token，失败返回null
     */
    String login(LoginQo loginQo);

    /**
     * 根据用户id获取用户信息
     *
     * @return 用户实体
     */
    UserVo getUserInfo(String userId);

    /**
     * 获取用户详细信息(主要是权限相关的)
     * @param userId 用户的id
     * @return 用户信息组装的实体
     */
    UserInfoVo getInfo(String userId);

    /**
     * 设置一部分用户信息
     * @param settingDTO
     * @return
     */
    Integer setUserInfo(String userId, SettingQo settingDTO);
}
