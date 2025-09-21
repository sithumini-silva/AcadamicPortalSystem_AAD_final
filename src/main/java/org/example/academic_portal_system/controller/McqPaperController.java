package org.example.academic_portal_system.controller;

import org.example.academic_portal_system.dto.QuestionDTO;
import org.example.academic_portal_system.repo.QuestionRepo;
import org.example.academic_portal_system.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/question")
@CrossOrigin(origins = "*")
public class McqPaperController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private QuestionRepo questionRepo;


    @GetMapping("get")
    public List<QuestionDTO> getQuestion() {
        List<QuestionDTO> questionDTOS = paperService.getAllQuestions();
        return questionDTOS;
    }


    @GetMapping("/{examId}")
    public List<QuestionDTO> getQuestionsByExamId(@PathVariable Integer examId) {
        return paperService.getQuestionsByExamId(examId);
    }


//    @PostMapping("/submit")
//    public ResponseEntity<Integer> submitAnswers(@RequestBody Map<Integer, List<Integer>> userAnswers) {
//        int score = paperService.calculateScore(userAnswers);
//        return ResponseEntity.ok(score);
//    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitAnswers(@RequestBody Map<Integer, List<Integer>> userAnswers) {
        int score = paperService.calculateScore(userAnswers);
        int totalQuestions = paperService.getTotalQuestions();
        double percentage = (score * 100.0) / totalQuestions;


        String grade = calculateGrade(percentage);


        Map<String, Object> result = new HashMap<>();
        result.put("score", score);
        result.put("totalQuestions", totalQuestions);
        result.put("percentage", percentage);
        result.put("grade", grade);

        return ResponseEntity.ok(result);
    }

    private String calculateGrade(double percentage) {
        if (percentage >= 75) {
            return "A";
        } else if (percentage >= 65) {
            return "B";
        } else if (percentage >= 55) {
            return "C";
        } else if (percentage >= 40) {
            return "S";
        } else {
            return "F";
        }
    }

    //*******************************************
    @PostMapping("/submit1")
    public ResponseEntity<Map<String, Object>> submitAnswer(@RequestBody Map<Integer, List<Integer>> userAnswers,
                                                            @RequestParam int examId) {

        int totalQuestions = paperService.getTotalQuestionsForExam(examId);

        int score = paperService.calculateScores(userAnswers, examId);
        double percentage = (score * 100.0) / totalQuestions;

        String grade = calculateGrade(percentage);

        Map<String, Object> result = new HashMap<>();
        result.put("score", score);
        result.put("totalQuestions", totalQuestions);
        result.put("percentage", percentage);
        result.put("grade", grade);

        return ResponseEntity.ok(result);
    }



    private int getTotalQuestions() {

        return 10;
    }

    private boolean isCorrectAnswer(int qid, List<Integer> selectedAnswers) {
        List<Integer> correctAnswers = questionRepo.findCorrectAnswersByQid(qid);


        return new HashSet<>(correctAnswers).equals(new HashSet<>(selectedAnswers));
    }


}
