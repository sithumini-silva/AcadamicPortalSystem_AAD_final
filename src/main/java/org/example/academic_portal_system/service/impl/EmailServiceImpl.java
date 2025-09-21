package org.example.academic_portal_system.service.impl;

import org.example.academic_portal_system.entity.Email;
import org.example.academic_portal_system.repo.EmailRepo;
import org.example.academic_portal_system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepo emailRepository;

//    @Override
//    public void sendEmail(String to, String subject, String body) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("divyanjaleesilva88@gmail.com");
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(body);
//            mailSender.send(message);
//        } catch (Exception e) {
//            e.printStackTrace(); // print the error in console for debugging
//        }
//    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            // Split the 'to' field (comma-separated) into an array of email addresses
            String[] recipients = to.split("\\s*,\\s*");  // Splitting by commas, allowing for optional spaces

            // Create the email message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("sithusilva94@gmail.com");
            message.setTo(recipients);  // Set the multiple recipients
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            mailSender.send(message);


            System.out.println("Email sent to: " + String.join(", ", recipients));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveEmailRecord(String to, String subject, String body) {
        Email email = new Email(to, subject, body, LocalDateTime.now());
        emailRepository.save(email);
        System.out.println("Email record saved successfully.");
    }

    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }


    public boolean deleteEmail(int id) {
        if (emailRepository.existsById(id)) {
            emailRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
