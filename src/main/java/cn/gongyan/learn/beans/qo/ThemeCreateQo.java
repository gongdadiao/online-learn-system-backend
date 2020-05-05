package cn.gongyan.learn.beans.qo;

import lombok.Data;

/**
 * @author 龚研
 * @desc ThemeCreateQo
 * @date 2020/4/13
 * @qq 1085704190
 **/

@Data
public class ThemeCreateQo {
    private String themeClassId;

    private String themeName;

    private Long themeSeq;

    private String themeDescription;
}
