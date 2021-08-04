package com.telega.test.repository;

import com.telega.test.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaQuestionRepository extends JpaRepository<Question, Integer> {
// А здесь мы написали SQL Query, которая будет выбирать 1 случайный вопрос из таблицы вопросов
    @Query(nativeQuery = true, value = "SELECT *  FROM java_quiz ORDER BY random() LIMIT 1")
    Question getRandomQuestion();
}
