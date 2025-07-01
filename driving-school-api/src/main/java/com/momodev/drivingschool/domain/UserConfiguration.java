package com.momodev.drivingschool.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_configuration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    // Aquí indicamos al builder que use este DEFAULT
    @Builder.Default
    @Column(name = "dark_mode", nullable = false)
    private Boolean darkMode = Boolean.FALSE;

    @Column(name = "created_at", updatable = false, columnDefinition = "timestamp default current_timestamp")
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private java.sql.Timestamp updatedAt;
}
