package org.example.academic_portal_system.service.impl;

import org.example.academic_portal_system.dto.QuestionDTO;
import org.example.academic_portal_system.dto.QuestionsDTO;
import org.example.academic_portal_system.entity.Question;
import org.example.academic_portal_system.exception.ResourceNotFoundException;
import org.example.academic_portal_system.repo.ExamRepo;
import org.example.academic_portal_system.repo.QuestionRepo;
import org.example.academic_portal_system.repo.UserRepo;
import org.example.academic_portal_system.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private ExamRepo examRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public Question addQuestion(Question question) {
        // Check if 20 questions already exist for this MCQ number
        List<Question> existingQuestions = questionRepo.findByMcqNumber(question.getMcqNumber());
        if (existingQuestions.size() >= 20) {
            throw new IllegalArgumentException("Cannot add more than 20 questions for this MCQ Number.");
        }

        return questionRepo.save(question);
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionRepo.findAll();
        return questions.stream().map(question -> {
            QuestionDTO dto = modelMapper.map(question, QuestionDTO.class);

            // Set userId in the DTO. Handle case where user might be null
            dto.setUserId(question.getUser() != null ? question.getUser().getU_id() : 0);  // Assuming userId is set as 0 when user is null

            // Optionally, set other fields if needed. For example, if examId is missing, you can set it here:
            dto.setExamId(question.getExam() != null ? question.getExam().getId() : 0);  // Assuming examId is set as 0 when exam is null

            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public List<Question> findByName(String name) {
        return questionRepo.findByNameContainingIgnoreCase(name);
    }


    public boolean updateQuestion(QuestionsDTO questionsDTO) {
        Question question = questionRepo.findById(questionsDTO.getQid())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        question.setContent(questionsDTO.getContent());
        question.setOption1(questionsDTO.getOption1());
        question.setOption2(questionsDTO.getOption2());
        question.setOption3(questionsDTO.getOption3());
        question.setOption4(questionsDTO.getOption4());
        question.setAnswer(questionsDTO.getAnswer());
        question.setMcqNumber(questionsDTO.getMcqNumber());


        questionRepo.save(question);
        return false;
    }




    @Override
    public void deleteQuestion(Long questionId) {
        this.questionRepo.deleteById(questionId);
    }

    @Override
    public Set<Question> getQuestions() {
        return new HashSet<>(this.questionRepo.findAll());
    }

    @Override
    public Question getQuestion(Long questionId) {
        return this.questionRepo.findById(questionId).orElse(null);
    }

    @Override
    public List<Question> getQuestionsByMcq(int mcqNumber) {
        return questionRepo.findByMcqNumber(mcqNumber);
    }

    @Override
    public List<Question> findQuestionsByMcqNumber(int mcqNumber) {
        return questionRepo.findByMcqNumber(mcqNumber);
    }

    public List<Question> getAllQuestion() {
        return questionRepo.findAll();
    }

    public List<Question> getQuestionsByExam(Long examId) {
        return questionRepo.findByExamId(examId);
    }



    //****************************
    @Override
    public List<Question> getQuestionsByExams(Long examId) {
        List<Question> questions = questionRepo.findByExamId(examId);
        if (questions.isEmpty()) {
            throw new IllegalArgumentException("No questions found for the given exam.");
        }
        return questions;
    }


}
