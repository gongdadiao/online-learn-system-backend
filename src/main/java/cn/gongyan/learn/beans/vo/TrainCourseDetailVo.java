package cn.gongyan.learn.beans.vo;

import cn.gongyan.learn.beans.entity.TrainningQuestion;
import lombok.Data;

import java.util.List;

@Data
public class TrainCourseDetailVo {
    String avartar;
    String title;
    String desc;
    List<TrainningQuestion> questionList;
}