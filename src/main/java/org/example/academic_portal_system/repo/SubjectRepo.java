package org.example.academic_portal_system.repo;

import org.example.academic_portal_system.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Integer> {
//    @Query("SELECT MAX(s.id) FROM Subject s")
//    Integer findMaxId();

    @Query("SELECT MAX(s.id) FROM Subject s WHERE s.id IS NOT NULL")
    Integer findMaxId();


        // Custom query to fetch all subject IDs
        @Query("SELECT s.id FROM Subject s")
        List<Integer> findAllIds();



}
