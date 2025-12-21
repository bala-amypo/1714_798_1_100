package com.example.demo.repository;

import com.example.demo.model.ComplianceScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ComplianceScoreRepository extends JpaRepository<ComplianceScore, Long> {
    Optional<ComplianceScore> findByVendorId(Long vendorId);
    
    // Add these methods if you want to use them:
    // @Query("SELECT cs FROM ComplianceScore cs WHERE cs.rating = :rating")
    // List<ComplianceScore> findByRating(@Param("rating") String rating);
    
    // @Query("SELECT cs FROM ComplianceScore cs WHERE cs.scoreValue >= :minScore AND cs.scoreValue <= :maxScore")
    // List<ComplianceScore> findByScoreRange(@Param("minScore") Double minScore, @Param("maxScore") Double maxScore);
    
    // @Query("SELECT cs FROM ComplianceScore cs ORDER BY cs.scoreValue DESC")
    // List<ComplianceScore> findAllOrderByScoreDesc();
}