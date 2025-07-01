package com.momodev.drivingschool.repository;

import com.momodev.drivingschool.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategoryIdIn(List<Integer> categoryIds);
}
