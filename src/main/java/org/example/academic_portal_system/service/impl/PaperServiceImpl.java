package org.example.academic_portal_system.service.impl;

import org.example.academic_portal_system.dto.QuestionDTO;
import org.example.academic_portal_system.entity.Question;
import org.example.academic_portal_system.repo.QuestionRepo;
import org.example.academic_portal_system.service.PaperService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QuestionRepo questionRepository;

    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> {
            QuestionDTO dto = modelMapper.map(question, QuestionDTO.class);


            dto.setUserId(question.getUser() != null ? question.getUser().getU_id() : 0);

            dto.setExamId(question.getExam() != null ? question.getExam().getId() : 0);

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Question> getAllQuestion() {
        return null;
    }

    @Override
    public List<Map<String, Object>> getQuestion() {
        List<Question> questions = questionRepository.findAll();

        return questions.stream().map(question -> {
            Map<String, Object> questionMap = new HashMap<>();
            questionMap.put("qid", question.getQid());
            questionMap.put("name", question.getName());
            questionMap.put("content", question.getContent());
            questionMap.put("option1", question.getOption1());
            questionMap.put("option2", question.getOption2());
            questionMap.put("option3", question.getOption3());
            questionMap.put("option4", question.getOption4());
            questionMap.put("answer", question.getAnswer());
            questionMap.put("mcqNumber", question.getMcqNumber());

            questionMap.put("userId", question.getUser() != null ? question.getUser().getU_id() : 0);
            questionMap.put("examId", question.getExam() != null ? question.getExam().getId() : 0);

            return questionMap;
        }).collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsByExamId(Integer examId) {
        return questionRepository.findByExamId(examId);
    }

    @Override
    public int getTotalQuestions() {
        return (int) questionRepository.count();
    }

    @Override
    public int calculateScore(Map<Integer, List<Integer>> userAnswers) {
        int score = 0;

        for (Map.Entry<Integer, List<Integer>> entry : userAnswers.entrySet()) {
            int qid = entry.getKey();
            List<Integer> selectedAnswers = entry.getValue();

            List<Integer> correctAnswers = questionRepository.findCorrectAnswersByQid(qid);

            if (selectedAnswers.containsAll(correctAnswers) && correctAnswers.containsAll(selectedAnswers)) {
                score++;
            }
        }

        return score;
    }

    //*********************************************************
    @Override
    public int calculateScores(Map<Integer, List<Integer>> userAnswers, int examId) {
        int score = 0;

        for (Map.Entry<Integer, List<Integer>> entry : userAnswers.entrySet()) {
            int qid = entry.getKey();
            List<Integer> selectedAnswers = entry.getValue();

            List<Integer> correctAnswers = questionRepository.findCorrectAnswersByQid(qid);

            if (selectedAnswers.containsAll(correctAnswers) && correctAnswers.containsAll(selectedAnswers)) {
                score++;
            }
        }

        return score;
    }


    public int getTotalQuestionsForExam(int examId) {
        return questionRepository.countQuestionsByExamId(examId);
    }

}
