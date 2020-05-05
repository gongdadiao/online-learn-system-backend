package cn.gongyan.learn.beans.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 龚研
 * @desc ClassesInfoVo
 * @date 2020/4/20
 * @qq 1085704190
 **/

@Data
public class ClassesInfoVo {
    private String teacherName;
    private String teacherAvatar;
    private String teacherDesc;
    private Date startTime;
    private Date endTime;
    private String term;
    private String classDesc;

    private String classType;

    private String classAvatar;
    private String classTitle;

    private String desc;

    private String goal;

    private String outline;

    private String preKnowledge;

    private String request;

    private String information;


}
