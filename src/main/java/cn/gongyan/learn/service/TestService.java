package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.Chapter;
import cn.gongyan.learn.beans.entity.Question;
import cn.gongyan.learn.beans.entity.Resource;
import cn.gongyan.learn.repository.ChapterRepository;
import cn.gongyan.learn.repository.QuestionRepository;
import cn.gongyan.learn.repository.ResourceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 龚研
 * @desc TestService
 * @date 2020/5/17
 * @qq 1085704190
 **/
@Service
@Transactional
public class TestService {
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    QuestionRepository questionRepository;

    public ArrayList<Question> getQuestionArray(String chapterId){
        Chapter chapter = chapterRepository.findById(chapterId).orElse(null);
        Resource resource = resourceRepository.findById(chapter.getChResId()).orElse(null);
        assert(resource.getResType().compareTo("homework")==0);
        String[] strs = resource.getResUrl().split(",");
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (String s : strs) {
            arrayList.add(Integer.parseInt(s));
        }
        ArrayList<Question> list = new ArrayList<>();
        for (Integer id : arrayList) {
            Question question = questionRepository.findById(id).orElse(null);
            //question.setQuestionAnswers(null);
            Question question1 = new Question();
            BeanUtils.copyProperties(question,question1);
            question1.setQuestionAnswers(null);
            list.add(question1);
        }
        return list;
    }
}
