/***********************************************************
 * @Description : 资源服务
 * @author      : 龚研
 * @date        : 2020-03-28 07:20
 * @qq          : 1085704190
 ***********************************************************/
package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.Question;
import cn.gongyan.learn.beans.entity.Resource;
import cn.gongyan.learn.beans.entity.Theme;
import cn.gongyan.learn.beans.qo.ResourceQo;
import cn.gongyan.learn.beans.vo.ResourcePageVo;
import cn.gongyan.learn.beans.vo.ResourceVo;
import cn.gongyan.learn.repository.QuestionRepository;
import cn.gongyan.learn.repository.ResourceRepository;
import cn.gongyan.learn.repository.UserRepository;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ResourceService {

    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionRepository questionRepository;

    /**
     * 获取资源
     * @param pageNo
     * @param pageSize
     * @return
     */
    public ResourcePageVo getResourceList(Integer pageNo, Integer pageSize) {
        // 获取考试列表
        // 按照日期降序排列
        Sort sort = new Sort(Sort.Direction.DESC, "resUploadDate");
        // 构造分页请求,注意前端面页面的分页是从1开始的，后端是从0开始地，所以要减去1哈
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<Resource> resPage = resourceRepository.findAll(pageRequest);
        ResourcePageVo resPageVo = new ResourcePageVo();
        // 设置页码
        resPageVo.setPageNo(pageNo);
        // 设置每页有多少条数据
        resPageVo.setPageSize(pageSize);
        // 设置总共有多少个元素
        resPageVo.setTotalCount(resPage.getTotalElements());
        // 设置一共有多少页
        resPageVo.setTotalPage(resPage.getTotalPages());
        // 取出当前页的考试列表
        List<Resource> resList = resPage.getContent();
        // 需要自定义的exam列表
        List<ResourceVo> resVoList = new ArrayList<>();
        // 循环完成每个属性的定制
        for (Resource res : resList) {
            ResourceVo resVo = new ResourceVo();
            // 先尽量复制能复制的所有属性
            BeanUtils.copyProperties(res, resVo);
            Instant instant = res.getResUploadDate().toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            LocalDate localDate = localDateTime.toLocalDate();
            resVo.setResUploadDate(localDate);
            // 设置问题的创建者
            resVo.setResCreator(
                    Objects.requireNonNull(
                            userRepository.findById(
                                    res.getResCreatorId()
                            ).orElse(null)
                    ).getUserUsername()
            );
            // 把examVo加到examVoList中
            resVoList.add(resVo);
        }
        resPageVo.setResourceVoList(resVoList);
        return resPageVo;
    }

    /**
     * 获取家庭作业的题目
     * @param ids
     * @return
     */
    public List<Question> getHomeworkQuestions(List<Integer> ids) {
        ArrayList<Question> list = new ArrayList<>();
        for (Integer id : ids) {
            Question question = questionRepository.findById(id).orElse(null);
            //question.setQuestionAnswers(null);
            Question question1 = new Question();
            BeanUtils.copyProperties(question,question1);
            question1.setQuestionAnswers(null);
            list.add(question1);
        }
        return list;
    }

    public JSONArray getResourceForChapter(Integer number){
        JSONArray result = new JSONArray();
        List<Resource> list = resourceRepository.findAll();
        JSONObject examObj=new JSONObject();
        examObj.put("value","考试");
        examObj.put("label","考试");
        JSONObject testObj=new JSONObject();
        testObj.put("value","作业");
        testObj.put("label","作业");
        JSONObject pptObj=new JSONObject();
        pptObj.put("value","ppt");
        pptObj.put("label","ppt");
        JSONObject wordObj=new JSONObject();
        wordObj.put("value","文档");
        wordObj.put("label","文档");
        JSONObject javaObj=new JSONObject();
        javaObj.put("value","源码");
        javaObj.put("label","源码");
        JSONObject videoObj=new JSONObject();
        videoObj.put("value","视频");
        videoObj.put("label","视频");
        //设置下一个队列
        JSONArray examArr=new JSONArray();
        JSONArray testArr=new JSONArray();
        JSONArray pptArr=new JSONArray();
        JSONArray wordArr=new JSONArray();
        JSONArray javaArr=new JSONArray();
        JSONArray videoArr=new JSONArray();
        for (Resource res : list) {
            JSONObject object = new JSONObject();
            object.put("label",res.getResName());
            object.put("value",res.getResId());
            object.put("type",res.getResType());
            if(res.getResType().compareTo("考试")==0){
                examArr.add(object);
            }else if(res.getResType().compareTo("作业")==0){
                testArr.add(object);
            }else if(res.getResType().compareTo("ppt")==0){
                pptArr.add(object);
            }else if(res.getResType().compareTo("word文档")==0){
                wordArr.add(object);
            }else if(res.getResType().compareTo("源码")==0){
                javaArr.add(object);
            }else if(res.getResType().compareTo("视频")==0){
                videoArr.add(object);
            }
        }
        examObj.put("children",examArr);
        testObj.put("children",testArr);
        pptObj.put("children",pptArr);
        javaObj.put("children",javaArr);
        wordObj.put("children",wordArr);
        videoObj.put("children",videoArr);
        //设置最终结果
        result.add(examObj);
        result.add(testObj);
        result.add(pptObj);
        result.add(wordObj);
        result.add(javaObj);
        result.add(videoObj);

        return result;
    }



//    public JSONObject getThemes() {
//        JSONObject object = new JSONObject();
//        List<Theme> list = themeMapper.getThemes();
//
//        JSONObject base = new JSONObject();
//        base.put("title","Java在线学习课程 从入门到实战学习资料");
//        base.put("rank","初级");
//        base.put("duration", "一学期");
//        base.put("number",207);
//        base.put("praise", 100);
//        base.put("isComplete", true);
//
//        JSONObject teacher = new JSONObject();
//        teacher.put("avatar","https://img1.sycdn.imooc.com/user/5abe468b0001664107390741-100-100.jpg");
//        teacher.put("name","一页居士");
//        teacher.put("job","桂电学子");
//        teacher.put("introduce","劝君莫弹食客铗，劝君莫扣富儿门。残羹冷炙有德色，不如著书黄叶村。");
//
//
//        JSONObject chapter = new JSONObject();
//        chapter.put("introduce","");
//        JSONArray chapter_data = new JSONArray();
//        for (Theme item : list) {
//            JSONObject chapter_data_item = new JSONObject();
//            chapter_data_item.put("title",item.getThemeName());
//            JSONArray chapter_data_item_term = new JSONArray();
//            Integer index=1;
//            for (Resource res : item.getResourceList()) {
//                JSONObject term = new JSONObject();
//                term.put("id",res.getResId());
//                term.put("avatar",res.getResAvatar());
//                term.put("type",res.getResType());
//                term.put("url",res.getResUrl());
//                term.put("title",res.getResName());
//                term.put("rate",index);
//                index++;
//                chapter_data_item_term.add(term);
//            }
//            chapter_data_item.put("term",chapter_data_item_term);
//            chapter_data.add(chapter_data_item);
//        }
//        chapter.put("data",chapter_data);
//
//        object.put("base",base);
//        object.put("teacher",teacher);
//        object.put("chapter",chapter);
//
//        return object;
//    }



//    public Resource getOneResource(String resId) {
//        Resource resource = resourceMapper.findOneResouce(resId);
//        return resource;
//    }

    /**
     * 添加普通资源
     * @param resourceQo
     * @param userId
     * @return
     */
    public Integer addOneResource(ResourceQo resourceQo, String userId) {
        Resource resource=new Resource();
        BeanUtils.copyProperties(resourceQo,resource);
        String uuid = IdUtil.simpleUUID();
        resource.setResId(uuid);
        resource.setResCreatorId(userId);
        resource.setResUploadDate(new Date());
        System.out.println(resource.toString());
        if(resourceQo.getResType().compareTo("考试")==0){
            resource.setResUrl(" ");
            resource.setResExamId(resourceQo.getExamId());
        }
        Resource save = resourceRepository.save(resource);
        if(save==null)
            return 0;
        return 1;
    }
//    public Integer addHomework(List<Integer> ids, String userId) {
//        Resource resource = new Resource();
//
////        String uuid = IdUtil.simpleUUID();
////        resource.setResId(uuid);
////        resource.setResCreatorId(userId);
////        resource.setResUploadDate(LocalDate.now());
////        String id_str="";
////        for (Integer id : ids) {
////            id_str+=(","+id);
////        }
////        if(id_str.compareTo("")!=0){
////            id_str=id_str.substring(1);
////        }
////        resource.setResDescription(id_str);
////        resource.setResType("homework");
//
//        return resourceMapper.insert(resource);
//    }
}
