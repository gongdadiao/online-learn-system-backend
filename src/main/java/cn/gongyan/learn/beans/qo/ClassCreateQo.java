package cn.gongyan.learn.beans.qo;

import  lombok.Data;

import java.util.Date;

/**
 * @author 龚研
 * @desc ClassCreateQo
 * @date 2020/4/12
 * @qq 1085704190
 **/
@Data
public class ClassCreateQo {
    private String title;

    private String avartar;

    private Date startTime;

    private Date endTime;

    private String term;

    private String desc;

    private Integer type;

    private String classType;
}
