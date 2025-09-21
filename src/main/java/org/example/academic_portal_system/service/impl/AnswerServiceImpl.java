package org.example.academic_portal_system.service.impl;

import org.example.academic_portal_system.entity.Result;
import org.example.academic_portal_system.entity.User;
import org.example.academic_portal_system.repo.AnswerRepo;
import org.example.academic_portal_system.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepo answerRepository;

    public int checkAnswers(int examId, User student) {  // Change parameter to User
        List<Result> studentAnswers = answerRepository.findByExamIdAndStudent(examId, student);

        int score = 0;
        for (Result answer : studentAnswers) {
            if (answer.getMsg().equals(answer.getTotalMark())) {
                score++;
            }
        }
        return score;
    }
}
