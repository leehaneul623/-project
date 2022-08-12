package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Vo.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByMallUserId(Integer userId);
}
