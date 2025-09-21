package org.example.academic_portal_system.service;

import org.example.academic_portal_system.dto.QuestionDTO;
import org.example.academic_portal_system.dto.QuestionsDTO;
import org.example.academic_portal_system.entity.Question;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    Question addQuestion(Question question);
    boolean updateQuestion(QuestionsDTO questionDTO);
    void deleteQuestion(Long questionId);
    Set<Question> getQuestions();
    Question getQuestion(Long questionId);
    List<Question> getQuestionsByMcq(int mcqNumber);
    List<Question> findQuestionsByMcqNumber(int mcqNumber);

    List<Question> findByName(String name);

    public List<QuestionDTO> getAllQuestions();
//******************************************


    public List<Question> getAllQuestion();
//    public List<Question> getQuestionsByExam(Long examId);

    public List<Question> getQuestionsByExams(Long examId);
}
