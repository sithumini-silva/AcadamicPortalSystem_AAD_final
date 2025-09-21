package org.example.academic_portal_system.service;

import org.example.academic_portal_system.dto.QuestionDTO;
import org.example.academic_portal_system.entity.Question;

import java.util.List;
import java.util.Map;

public interface PaperService {

    public List<QuestionDTO> getQuestionsByExamId(Integer examId);

    public List<QuestionDTO> getAllQuestions();

    public List<Question> getAllQuestion();
    public List<Map<String, Object>> getQuestion();
    public int calculateScore(Map<Integer, List<Integer>> userAnswers);

    public int getTotalQuestions();

    //********************************************
    public int calculateScores(Map<Integer, List<Integer>> userAnswers, int examId);
    public int getTotalQuestionsForExam(int examId);
}
