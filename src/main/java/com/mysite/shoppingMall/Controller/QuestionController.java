package com.mysite.shoppingMall.Controller;

import com.mysite.shoppingMall.Form.QuestionForm;
import com.mysite.shoppingMall.Repository.QuestionRepository;
import com.mysite.shoppingMall.Repository.UserRepository;
import com.mysite.shoppingMall.Service.QuestionService;
import com.mysite.shoppingMall.Ut.Ut;
import com.mysite.shoppingMall.Vo.IsLogined;
import com.mysite.shoppingMall.Vo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserRepository userRepository;

    //C 생성 ==============================================
    @GetMapping("/doWrite")
    public String doWrite(QuestionForm questionForm){
        return "QnA/write.html";
    }

    @PostMapping("/doWrite")
    public String doWrite(@Valid QuestionForm questionForm, BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors()){
            return "QnA/write.html";
        }
        this.questionService.doWrite(questionForm.getSubject(), questionForm.getContent(), session);

        return "redirect:/question/list";
    }


    //R 읽기 ==============================================
    @GetMapping("/list")
    public String showQuestion(Model model, @PageableDefault(size = 2) Pageable pageable){
        Page<Question> questions = questionRepository.findAll(pageable);
        int startPage = Math.max(1, questions.getPageable().getPageNumber() - 4 );
        int endPage = Math.min(questions.getTotalPages(), questions.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("questions", questions);
        return "QnA/qna.html";
    }


    @RequestMapping("/detail") // 단건조회
    @ResponseBody

    public Question showDetail(Integer id){
        Question question = questionRepository.findById(id).get();

        return question;
    }


    //U 수정 ==============================================
    @RequestMapping("/doModify")
    @ResponseBody
    public String doModify(Integer id, String subject, String content){
        if(id == null){
            return "게시물 번호를 입력해주세요.";
        }
        if(Ut.empty(subject)){
            return "질문 번호를 입력해주세요.";
        }
        if(Ut.empty(content)){
            return "질문 내용을 입력해주세요.";
        }
        if(!questionRepository.existsById(id)){
            return "게시물이 없습니다.";
        }

        Question question = questionRepository.findById(id).get();

        question.setCreateDate(LocalDateTime.now());
        question.setSubject(subject);
        question.setContent(content);

        questionRepository.save(question);
        return "%d번 질문이 수정 되었습니다.".formatted(question.getId());
    }


    //D 삭제 ==============================================
    @RequestMapping("/doDelete")
    @ResponseBody
    public String doDelete(Integer id){
        if(!questionRepository.existsById(id)){
            return "%d번 게시물이 없습니다.".formatted(id);
        }

        Question question = questionRepository.findById(id).get();
        questionRepository.delete(question);

        return "%d번 게시물을 삭제했습니다.".formatted(id);
    }
}
