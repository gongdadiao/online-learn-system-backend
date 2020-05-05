package cn.gongyan.learn.beans.vo;

import lombok.Data;
import java.util.List;

/**
 * @author 龚研
 * @desc GradeChartsVo
 * @date 2020/4/23
 * @qq 1085704190
 **/
@Data
public class GradeChartsVo {
    List<Item> items;
    @Data
    public static class Item{
        String name;
        Integer value;
    }
}
