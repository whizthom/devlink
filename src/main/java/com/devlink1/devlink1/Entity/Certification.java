package com.devlink1.devlink1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Certification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String issuingOrganization;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateIssued;

    private String certificateUrl;

    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Certification{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", issuingOrganization='" + issuingOrganization + '\'' +
                ", dateIssued=" + dateIssued +
                ", certificateUrl='" + certificateUrl + '\'' +
                '}';
    }
}
