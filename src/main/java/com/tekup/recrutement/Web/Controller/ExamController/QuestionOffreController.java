package com.tekup.recrutement.Web.Controller.ExamController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.tekup.recrutement.Business.services.ExamServices.QuestionService;
import com.tekup.recrutement.Business.services.ExamServices.QuizService;
import com.tekup.recrutement.DAO.entities.ExamEntities.Question;
import com.tekup.recrutement.DAO.entities.ExamEntities.Quiz;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionOffreController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @PostMapping("/")
    public ResponseEntity<Question> add(@RequestBody Question question) {

        Long quizId = question.getQuiz().getQId();
        System.out.println("Received quiz ID from request: " + quizId); // Vérifiez l'ID du quiz extrait

        Quiz quiz = quizService.getQuiz(quizId);

        // Assurer que l'instance de quiz existe
        if (quiz == null) {
            System.out.println("Quiz not found with ID: " + quizId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Ou renvoyer un message d'erreur
                                                                             // approprié
        }

        // Associer l'instance de quiz à la question
        question.setQuiz(quiz);

        // Sauvegarder la question
        Question savedQuestion = questionService.addQuestion(question);

        // Retourner la réponse avec la question sauvegardée
        return ResponseEntity.ok(savedQuestion);
    }

    // update question
    @PutMapping("/")
    public ResponseEntity<Question> update(@RequestBody Question question) {
        return ResponseEntity.ok(this.questionService.updateQuestion(question));
    }

    // get all questions of any quiz
    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid) {

        // Quiz quiz = new Quiz();
        // quiz.setQId(qid);
        // Set<Question> questionsOfQuiz =
        // this.questionService.getQuestionsOfQuiz(quiz);
        // return ResponseEntity.ok(questionsOfQuiz);

        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Question> questions = quiz.getQuestions();
        List list = new ArrayList(questions);

        if (list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) {
            list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions() + 1));
        }

        Collections.shuffle(list);
        return ResponseEntity.ok(list);
    }

    //

    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid) {

        Quiz quiz = new Quiz();
        quiz.setQId(qid);
        Set<Question> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questionsOfQuiz);

        // return ResponseEntity.ok(list);
    }

    // get singele question
    @GetMapping("/{quesId}")
    public Question get(@PathVariable("quesId") Long quesId) {
        return this.questionService.getQuestion(quesId);

    }

    // delete question
    @DeleteMapping("/{quesId}")
    public void delete(@PathVariable("quesId") Long quesId) {
        this.questionService.deleteQuestion(quesId);
    }

}
