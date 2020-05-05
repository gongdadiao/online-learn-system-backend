package cn.gongyan.learn.beans.qo;

import lombok.Data;

/**
 * @author 龚研
 * @desc ChapterCreateQo
 * @date 2020/4/13
 * @qq 1085704190
 **/

@Data
public class ChapterCreateQo {
    private String title;

    private Integer themeId;

    private String type;

    private String resId;

    private Integer seq;

}
