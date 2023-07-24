package com.tmtnt.studentreview.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Assignment {
    @Id
    @GeneratedValue
    private Long id;
    private String status;
    private String githubUrl;
    private String branch;
    @ManyToOne
    private User user;

}
