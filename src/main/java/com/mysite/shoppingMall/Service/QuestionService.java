package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Repository.QuestionRepository;
import com.mysite.shoppingMall.Vo.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList(Integer userId){
        List<Question> questionList = questionRepository.findByMallUserId(userId);
        System.out.println(questionList);
        return questionList;
    }

    public void doWrite(String subject, String content){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }
}
