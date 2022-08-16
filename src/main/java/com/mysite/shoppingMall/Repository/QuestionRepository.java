package com.mysite.shoppingMall.Repository;

import com.mysite.shoppingMall.Vo.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByMallUserId(Integer userId);
    Page<Question> findAll(Pageable pageable);

}
