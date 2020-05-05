package cn.gongyan.learn.enums;

/**
 * @author 龚研
 * @desc StudentExcelEnum
 * @date 2020/4/18
 * @qq 1085704190
 **/

import lombok.Getter;


@Getter
public enum StudentExcelEnum {
    NAME(0,"姓名"),
    SCHOOL(1,"学校"),
    GRADE(2,"年级"),
    STUDENTID(3,"学号");

    StudentExcelEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    Integer key;
    String value;
}
