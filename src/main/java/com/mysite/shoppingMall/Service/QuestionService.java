package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Repository.QuestionRepository;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Vo.IsLogined;
import com.mysite.shoppingMall.Vo.MallUser;
import com.mysite.shoppingMall.Vo.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    public List<Question> getList(Integer userId){
        List<Question> questionList = questionRepository.findByMallUserId(userId);
        System.out.println(questionList);
        return questionList;
    }

    public void doWrite(String subject, String content, HttpSession session){
        IsLogined isLogined = Ut.isLogined(session);
        MallUser mallUser = userRepository.findById(isLogined.getUserId()).get();
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setMallUser(mallUser);
        this.questionRepository.save(q);
    }
}
