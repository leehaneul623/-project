package com.mysite.shoppingMall.Service;

import com.mysite.shoppingMall.Repository.QuestionRepository;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Vo.IsLogined;
import com.mysite.shoppingMall.Vo.MallUser;
import com.mysite.shoppingMall.Vo.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    public Page<Question> getList(Integer page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        return this.questionRepository.findAll(pageable);
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
