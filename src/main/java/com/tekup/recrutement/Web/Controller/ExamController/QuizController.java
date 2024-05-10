package com.tekup.recrutement.Web.Controller.ExamController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekup.recrutement.Business.services.ExamServices.QuizService;
import com.tekup.recrutement.DAO.entities.ExamEntities.Category;
import com.tekup.recrutement.DAO.entities.ExamEntities.Quiz;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "http://localhost:4200")
public class QuizController {

    @Autowired
    private QuizService quizService;

    //add quiz
    @PostMapping("/")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz){
        
        return ResponseEntity.ok(this.quizService.addQuiz(quiz));
    }


    //update quiz
        @PutMapping("/")
        public ResponseEntity<Quiz> updateQuiz (@RequestBody Quiz quiz) {

            return ResponseEntity.ok(this.quizService.updateQuiz(quiz));
        }
    


    //get all quizzes
    @GetMapping("/")
    public ResponseEntity<?> quizzes(){
        return ResponseEntity.ok(this.quizService.getQuizzes());
    }

    //get the single quiz 
    @GetMapping("/{qid}")
    public Quiz quiz(@PathVariable("qid") Long qid) {
        return this.quizService.getQuiz(qid);
    }

    //delete quiz
    @DeleteMapping("/{qid}")
    public void delete(@PathVariable("qid") Long qid) {
        this.quizService.deleteQuiz(qid);
    }


    @GetMapping("/category/{cid}")
    public List <Quiz> getQuizzesOfCategory(@PathVariable("cid") Long cid) {

        Category category = new Category();
        category.setCid(cid);
        return this.quizService.getQuizzesByCategory(category);
         
    }

    //get active quizzes  
    @GetMapping("/active")
    public List <Quiz> getActiveQuizzes() {
        return this.quizService.getActiveQuizzes();
    }

    //get active quizzes of category
    @GetMapping("/category/active/{cid}")
    public List <Quiz> getActiveQuizzesOfCategory(@PathVariable("cid") Long cid) {
        Category category = new Category();
        category.setCid(cid);
        return this.quizService.getActiveQuizzesOfCategory(category);
    }

}

    
