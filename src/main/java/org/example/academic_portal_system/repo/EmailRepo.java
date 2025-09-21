package org.example.academic_portal_system.repo;

import org.example.academic_portal_system.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepo extends JpaRepository<Email, Integer> {

}
