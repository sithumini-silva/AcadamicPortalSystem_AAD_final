package org.example.academic_portal_system.controller;

import org.example.academic_portal_system.entity.Email;
import org.example.academic_portal_system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam String to,
                                            @RequestParam String subject,
                                            @RequestParam String body) {
        emailService.sendEmail(to, subject, body);
        return ResponseEntity.ok("Email sent successfully");
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveEmail(@RequestParam String to,
                                            @RequestParam String subject,
                                            @RequestParam String body) {
        try {
            // Save the email record in the database
            emailService.saveEmailRecord(to, subject, body);
            return ResponseEntity.ok("Email record saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/inbox")
    public ResponseEntity<List<Email>> getInboxEmails() {
        List<Email> emails = emailService.getAllEmails();
        return ResponseEntity.ok(emails);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteEmail(@PathVariable int id) {
        return emailService.deleteEmail(id); // deleteEmail method eka call karanna
    }




}
