package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.UserContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserContentRepository extends JpaRepository<UserContent, Integer> {

    // ✅ Para listar todos los contenidos leídos por un usuario
    List<UserContent> findByUser_Id(Integer userId);

    // ✅ Para verificar si un contenido ya fue leído por un usuario
    Optional<UserContent> findByUser_IdAndContent_Id(Integer userId, Integer contentId);
}
