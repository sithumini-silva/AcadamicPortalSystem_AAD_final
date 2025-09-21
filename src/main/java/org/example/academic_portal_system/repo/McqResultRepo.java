package org.example.academic_portal_system.repo;

import org.example.academic_portal_system.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McqResultRepo extends JpaRepository<Result, Integer> {
}
