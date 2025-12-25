package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_scores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false, unique = true)
    private Vendor vendor;
    
    @Column(nullable = false)
    private Double scoreValue = 0.0;
    
    private LocalDateTime lastEvaluated;
    
    private String rating;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplianceScore)) return false;
        return id != null && id.equals(((ComplianceScore) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "ComplianceScore{" +
                "id=" + id +
                ", scoreValue=" + scoreValue +
                ", rating='" + rating + '\'' +
                '}';
    }
}