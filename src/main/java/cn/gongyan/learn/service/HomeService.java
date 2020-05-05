package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.Classes;
import cn.gongyan.learn.beans.entity.HomeNav;
import cn.gongyan.learn.beans.entity.HomeNavMaintag;
import cn.gongyan.learn.beans.entity.HomeNavTags;
import cn.gongyan.learn.beans.jsonobject.home.HomeCourseRecommend;
import cn.gongyan.learn.beans.jsonobject.home.HomeNavData;
import cn.gongyan.learn.repository.ClassesRepository;
import cn.gongyan.learn.repository.HomeNavMainTagRepository;
import cn.gongyan.learn.repository.HomeNavRepository;
import cn.gongyan.learn.repository.HomeNavTagsRepository;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import sun.reflect.generics.repository.ClassRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 龚研
 * @desc HomeService
 * @date 2020/4/19
 * @qq 1085704190
 **/
@Service
@Transactional
public class HomeService {
    @Autowired
    ClassesRepository classRepository;
    @Autowired
    HomeNavRepository homeNavRepository;
    @Autowired
    HomeNavMainTagRepository homeNavMainTagRepository;
    @Autowired
    HomeNavTagsRepository homeNavTagsRepository;

    public List<HomeNavData> getHomeNavList(){
        List<HomeNav> navList = homeNavRepository.findAll();
        ArrayList<HomeNavData> homeNavDataArrayList = new ArrayList<>();
        for (HomeNav homeNav : navList) {
            //设置大标题
            HomeNavData homeNavData = new HomeNavData();
            homeNavData.setTitle(homeNav.getTitle());
            homeNavData.setType(homeNav.getType());
            //设置推荐课程
            HomeNavData.MainData mainData = new HomeNavData.MainData();
            ArrayList<HomeNavData.MainData.MainCourse> mainCourses = new ArrayList<>();
            Classes classes = new Classes();
            classes.setCNavRecommendId(homeNav.getId());
            List<Classes> classesList = classRepository.findAll(Example.of(classes));
            for (Classes item : classesList) {
                HomeNavData.MainData.MainCourse course = new HomeNavData.MainData.MainCourse();
                course.setImg(item.getCAvartar());
                course.setMoney("free");
                course.setNumber(item.getCTerm());
                try{
                    course.setRank(item.getCDescription().substring(0,5)+"...");
                }catch (StringIndexOutOfBoundsException e){
                    course.setRank(item.getCDescription());
                }
                course.setTitle(item.getCTitle());
                mainCourses.add(course);
            }
            mainData.setCourse(mainCourses);
            //设置标签
            HomeNavMaintag homeNavMaintag = new HomeNavMaintag();
            homeNavMaintag.setNavTitleId(homeNav.getId());
            List<HomeNavMaintag> homeNavMaintagList = homeNavMainTagRepository.findAll(Example.of(homeNavMaintag));
            ArrayList<HomeNavData.MainData.MainTag> mainTagArrayList = new ArrayList<>();
            for (HomeNavMaintag navMaintag : homeNavMaintagList) {
                HomeNavData.MainData.MainTag tag = new HomeNavData.MainData.MainTag();
                tag.setSubtitle(navMaintag.getSubtitle());
                HomeNavTags homeNavTags = new HomeNavTags();
                homeNavTags.setMaintagId(navMaintag.getId());
                List<HomeNavTags> homeNavTagsList = homeNavTagsRepository.findAll(Example.of(homeNavTags));
                ArrayList<String> stringArrayList = new ArrayList<>();
                for (HomeNavTags tags : homeNavTagsList) {
                    stringArrayList.add(tags.getContent());
                }
                tag.setList(stringArrayList);
                mainTagArrayList.add(tag);
            }
            mainData.setTags(mainTagArrayList);
            homeNavData.setData(mainData);
            homeNavDataArrayList.add(homeNavData);
        }
        return homeNavDataArrayList;
    }

    HomeCourseRecommend setOneRecommendedCourse(String title,String lIcon,String rIcon,String banner1Title, String banner1SubTitle,String banner2Title, String banner2SubTitle,Integer themeId){
        //设置一块内容
        HomeCourseRecommend recommend = new HomeCourseRecommend();
        recommend.setTitle(title);
        recommend.setLeftIcon(lIcon);
        recommend.setRightIcon(rIcon);
        ArrayList<HomeCourseRecommend.Banner> banners = new ArrayList<>();
        HomeCourseRecommend.Banner banner = new HomeCourseRecommend.Banner();
        banner.setTitle(banner1Title);
        banner.setSubtitle(banner1SubTitle);
        banner.setUrl("https://img.mukewang.com/5cbd837c0001b18b05670108.jpg");
        banners.add(banner);
        HomeCourseRecommend.Banner banner1 = new HomeCourseRecommend.Banner();
        banner1.setTitle(banner2Title);
        banner1.setSubtitle(banner2SubTitle);
        banner1.setUrl("https://img.mukewang.com/5bf224930001991905670108.jpg");
        banners.add(banner1);
        recommend.setBanner(banners);
        ArrayList<HomeCourseRecommend.Course> courseArrayList = new ArrayList<>();
        Classes classes = new Classes();
        classes.setCThemeId(themeId);
        List<Classes> classesList = classRepository.findAll(Example.of(classes));
        for (Classes item : classesList) {
            HomeCourseRecommend.Course course = new HomeCourseRecommend.Course();
            course.setName(item.getCTitle());
            course.setImg(item.getCAvartar());
            course.setId(item.getCId());
            //course.setNumber();
            try {
                course.setRank(item.getCDescription().substring(0,7)+"...");
            }catch (StringIndexOutOfBoundsException e){
                course.setRank(item.getCDescription());
            }

            course.setType(item.getCTerm());
            course.setPrice(0);
            course.setProcess(0);
            course.setStar(0);
            courseArrayList.add(course);
        }
        recommend.setData(courseArrayList);
        return recommend;
    }
    public JSONObject getCourses(){
        JSONObject object = new JSONObject();
        HomeCourseRecommend homeCourseRecommend = setOneRecommendedCourse(
                "实／战／推／荐",
                "center 0px",
                "center -36px",
                "站上Java项目风口，实现职业华丽转身",
                "入门技能+项目开发，逐步深入学习Java开发",
                "实践微课",
                "超值课程带来极致体验",
                4
        );
        object.put("practice",homeCourseRecommend);

        HomeCourseRecommend homeCourseRecommend1 = setOneRecommendedCourse(
                "前／沿／技／术",
                "center -72px",
                "center -108px",
                "站上Java项目风口，实现职业华丽转身",
                "入门技能+项目开发，逐步深入学习Java开发",
                "实践微课",
                "超值课程带来极致体验",
                3
        );
        object.put("new",homeCourseRecommend1);

        HomeCourseRecommend homeCourseRecommend2 = setOneRecommendedCourse(
                "专／项／发／展",
                "center -144px",
                "center -180px",
                "站上Java项目风口，实现职业华丽转身",
                "入门技能+项目开发，逐步深入学习Java开发",
                "实践微课",
                "超值课程带来极致体验",
                2
        );
        object.put("guide",homeCourseRecommend2);

        HomeCourseRecommend homeCourseRecommend3 = setOneRecommendedCourse(
                "技／能／提／升",
                "center -216px",
                "center -252px",
                "站上Java项目风口，实现职业华丽转身",
                "入门技能+项目开发，逐步深入学习Java开发",
                "实践微课",
                "超值课程带来极致体验",
                5
        );
        object.put("improve",homeCourseRecommend3);

        HomeCourseRecommend homeCourseRecommend4 = setOneRecommendedCourse(
                "基／础／课／程",
                "center -288px",
                "center -324px",
                "站上Java项目风口，实现职业华丽转身",
                "入门技能+项目开发，逐步深入学习Java开发",
                "实践微课",
                "超值课程带来极致体验",
                1
        );
        object.put("technology",homeCourseRecommend4);

        return object;
    }

}
