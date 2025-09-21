package org.example.academic_portal_system.repo;

import org.example.academic_portal_system.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepo extends JpaRepository<Result, Integer> {
    @Query("SELECT r.id FROM Result r")
    List<Integer> findAllIds();

}
