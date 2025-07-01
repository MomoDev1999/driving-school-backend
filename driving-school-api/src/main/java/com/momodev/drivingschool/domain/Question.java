package com.momodev.drivingschool.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private String statement;

    private String imageUrl;

    @Column(nullable = false, length = 255)
    private String wrongOne;
    @Column(nullable = false, length = 255)
    private String wrongTwo;
    @Column(nullable = false, length = 255)
    private String wrongThree;
    @Column(nullable = false, length = 255)
    private String correct;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "created_at", updatable = false, columnDefinition = "timestamp default current_timestamp")
    private java.sql.Timestamp createdAt;
}
