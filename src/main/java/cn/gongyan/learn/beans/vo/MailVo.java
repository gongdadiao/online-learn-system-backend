package cn.gongyan.learn.beans.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 龚研
 * @desc MailVo
 * @date 2020/4/23
 * @qq 1085704190
 **/

@Data
public class MailVo {

    private String mTitle;

    private String mSenderId;

    private String mReceiverId;

    private Date createTime;

    private String mContent;

    private String mClassId;

    String avartar;
    String name;
}
