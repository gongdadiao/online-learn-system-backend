/***********************************************************
 * @Description : 问题服务
 * @author      : 龚研
 * @date        : 2019-05-16 23:40
 * @qq          : 1085704190
 ***********************************************************/
package cn.gongyan.learn.service;

import cn.gongyan.learn.beans.entity.Question;
import cn.gongyan.learn.beans.jsonobject.OneQuestion;
import cn.gongyan.learn.beans.vo.QuestionPageVo;
import cn.gongyan.learn.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionService  {
    QuestionRepository questionRepository;
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public QuestionPageVo getQuestionsAsPage(Integer pageNo, Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        Page<Question> questionPage = questionRepository.findAll(pageRequest);
        QuestionPageVo questionPageVo = new QuestionPageVo();
        // 设置页码
        questionPageVo.setPageNo(pageNo);
        // 设置每页有多少条数据
        questionPageVo.setPageSize(pageSize);
        // 设置总共有多少个元素
        questionPageVo.setTotalCount(questionPage.getTotalElements());
        // 设置一共有多少页
        questionPageVo.setTotalPage(questionPage.getTotalPages());
        // 取出当前页的考试列表
        List<Question> content = questionPage.getContent();

        questionPageVo.setQuestionVoList(content);
        return questionPageVo;
    }


    public List<Question> getQuestions(Integer number) {
        PageRequest request = new PageRequest(0, number);
        Page<Question> all = questionRepository.findAll(request);
        List<Question> questionList = all.getContent();
        return questionList;
    }

    public Integer createOneQuestion(OneQuestion oneQuestion) {
        Question question = new Question();
        question.setQuestionAnswers(oneQuestion.getAnswer());
        question.setQuestionContent(oneQuestion.getQuestionContent());
        question.setQuestionDescription(oneQuestion.getQuestionDescription());
        question.setQuestionOptionSplit("#$");
        String options="";
        String type=oneQuestion.getQuestionType();
        if(type.compareTo("duoxuan")==0 || type.compareTo("danxuan")==0) {
            for (String questionOption : oneQuestion.getQuestionOptions()) {
                options = options + "#$" + questionOption;
            }
            if(options.length()>0)
                options=options.substring(2);
        }
        question.setQuestionSelections(options);
        question.setQuestionTypeName(oneQuestion.getQuestionType());
        Question save = questionRepository.save(question);
        return (save==null)?0:1;
    }
}
