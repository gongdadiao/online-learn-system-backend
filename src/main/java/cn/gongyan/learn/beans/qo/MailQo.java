package cn.gongyan.learn.beans.qo;

import lombok.Data;

/**
 * @author 龚研
 * @desc MailQo
 * @date 2020/4/21
 * @qq 1085704190
 **/
@Data
public class MailQo {
    String title;
    String content;
    String sendorId;
    String receivorId;
    String classId;
}
