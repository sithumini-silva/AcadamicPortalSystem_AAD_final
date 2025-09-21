package org.example.academic_portal_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerDTO {
        private int qid;
        private List<Integer> answers;

        // Getters and Setters
        public int getQid() {
            return qid;
        }

        public void setQid(int qid) {
            this.qid = qid;
        }

        public List<Integer> getAnswers() {
            return answers;
        }

        public void setAnswers(List<Integer> answers) {
            this.answers = answers;
        }


}
