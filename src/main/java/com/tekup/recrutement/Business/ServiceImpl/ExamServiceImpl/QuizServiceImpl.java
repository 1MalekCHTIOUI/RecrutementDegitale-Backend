package com.tekup.recrutement.Business.ServiceImpl.ExamServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekup.recrutement.Business.services.ExamServices.QuizService;
import com.tekup.recrutement.DAO.entities.QuizNotFoundException;
import com.tekup.recrutement.DAO.entities.ExamEntities.Category;
import com.tekup.recrutement.DAO.entities.ExamEntities.Quiz;
import com.tekup.recrutement.DAO.repositories.ExamRepositories.QuizRepository;


@Service
public class QuizServiceImpl implements QuizService{


    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Set<Quiz> getQuizzes() {
        return new HashSet<>(this.quizRepository.findAll());
    }

    // @Override
    // public Quiz getQuiz(Long quizId) {
    //     return this.quizRepository.findById(quizId).get();
    // }


    @Override
public Quiz getQuiz(Long quizId) {
    Optional<Quiz> quizOptional = this.quizRepository.findById(quizId);
    if (quizOptional.isPresent()) {
        return quizOptional.get();
    } else {
        // Gérer le cas où le quiz n'est pas trouvé
        throw new QuizNotFoundException("Quiz not found with id: " + quizId);
    }
}


    //@Override
    // public void deleteQuiz(Long quizId) {
        
    //     this.quizRepository.deleteById(quizId);
    
    // }
    @Override
    public void deleteQuiz(Long quizId) {
        System.out.println("Deleting quiz with ID: " + quizId);
         try {
            this.quizRepository.deleteById(quizId);
            System.out.println("Quiz deleted successfully");
         } catch (Exception e) {
              System.err.println("Error deleting quiz: " + e.getMessage());
            // Gérer l'erreur ici (par exemple, lancer une exception personnalisée)
    }
}

    @Override
    public List<Quiz> getQuizzesByCategory(Category category) {
        return this.quizRepository.findBycategory(category);
    }

    

    //get active quizzes

    @Override
    public List<Quiz> getActiveQuizzes() {
        return this.quizRepository.findByActive(true);
    }

    @Override
    public List<Quiz> getActiveQuizzesOfCategory(Category c) {
        return this.quizRepository.findBycategoryAndActive(c, true);
    }
    


    

}
