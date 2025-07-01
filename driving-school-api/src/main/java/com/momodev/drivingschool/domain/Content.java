package com.momodev.drivingschool.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "content")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    private String paragraph;

    @Column(name = "created_at", updatable = false, columnDefinition = "timestamp default current_timestamp")
    private java.sql.Timestamp createdAt;
}
