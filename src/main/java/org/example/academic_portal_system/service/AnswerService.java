package org.example.academic_portal_system.service;


import org.example.academic_portal_system.entity.User;

public interface AnswerService {
    public int checkAnswers(int examId, User student);
}
