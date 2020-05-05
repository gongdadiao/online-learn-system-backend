package cn.gongyan.learn.beans.qo;

import lombok.Data;

/**
 * @author 龚研
 * @desc RegisterQo
 * @date 2020/4/12
 * @qq 1085704190
 **/

@Data
public class RegisterQo {
    private String userName;
    private String nickName;
    private String password;
    private String password2;
    private String realName;
    private String studentId;
    private String school;
}
