package cn.gongyan.learn.beans.jsonobject.home;

import lombok.Data;

import java.util.List;

/**
 * @author 龚研
 * @desc HomeNavData
 * @date 2020/4/19
 * @qq 1085704190
 **/
@Data
public class HomeNavData {
    String title;
    String type;
    MainData data;
    @Data
    public static class MainData{
        List<MainTag> tags;
        List<MainCourse> course;

        @Data
        public static class MainTag{
            String subtitle;
            List<String> list;
        }
        @Data
        public static class MainCourse{
            String title;
            String img;
            String money;
            String rank;
            String number;
        }
    }
}
