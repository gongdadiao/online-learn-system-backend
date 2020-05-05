package cn.gongyan.learn.beans.qo;

import lombok.Data;

@Data
public class TrainQuestionCreateQo {
    private String title;
    private String desc;
    private String content;
    private String code;
    private String ans;
}