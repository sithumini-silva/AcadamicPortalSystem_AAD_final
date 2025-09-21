package org.example.academic_portal_system.service;


import org.example.academic_portal_system.entity.Email;

import java.util.List;

public interface EmailService {
    public void sendEmail(String to, String subject, String body);
    public void saveEmailRecord(String recipient, String subject, String body);
    public List<Email> getAllEmails();
    public boolean deleteEmail(int id);
}
