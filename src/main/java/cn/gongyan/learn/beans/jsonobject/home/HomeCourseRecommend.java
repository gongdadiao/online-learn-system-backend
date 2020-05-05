package cn.gongyan.learn.beans.jsonobject.home;

import lombok.Data;

import java.util.List;

/**
 * @author 龚研
 * @desc HomeCourseRecommend
 * @date 2020/4/19
 * @qq 1085704190
 **/
@Data
public class HomeCourseRecommend {
    String title;
    String leftIcon;
    String rightIcon;
    List<Course> data;
    List<Banner> banner;

    @Data
    public  static class Banner{
        String title;
        String subtitle;
        String url;
    }

    @Data
    public  static class Course{
        String id;
        String name;
        Integer process;
        List<String> tags;
        String type;
        String rank;
        Integer number;
        Integer star;
        Integer price;
        String img;
    }
}
