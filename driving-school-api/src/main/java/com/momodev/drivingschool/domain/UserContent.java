package com.momodev.drivingschool.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_content", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "content_id" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "content_id")
    private Content content;

    private java.sql.Timestamp readAt;
}
