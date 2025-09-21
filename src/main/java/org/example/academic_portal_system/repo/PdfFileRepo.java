package org.example.academic_portal_system.repo;

import org.example.academic_portal_system.entity.PdfFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfFileRepo extends JpaRepository<PdfFile, Integer> {


}
