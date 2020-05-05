package cn.gongyan.learn.beans.vo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * @author 龚研
 * @desc RecordMarkVo
 * @date 2020/4/21
 * @qq 1085704190
 **/
@Data
public class RecordMarkVo {
    String examRecordId;
    String examJoinerId;
    String examJoinerName;
    String examId;
    String chapterTitle;
    String examOrHomework;
    String examStatus;
    Date examJoinDate;
    Integer examJoinScore;
}
