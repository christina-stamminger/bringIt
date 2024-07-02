package com.codersnextdoor.bringIt.api.todo;


import com.codersnextdoor.bringIt.api.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByUserOfferedAndLocationAndTitleAndDescriptionAndAddInfoAndExpiresAt (
            User userOffered,
            String location,
            String title,
            String description,
            String addInfo,
            LocalDateTime expiresAt
    );

    Optional<Todo> findByTodoIdAndLocationAndTitleAndDescriptionAndAddInfoAndExpiresAt (
            Long TodoId,
            String location,
            String title,
            String description,
            String addInfo,
            LocalDateTime expiresAt
    );

    @Query("SELECT t FROM Todo t WHERE t.userOffered.userId = :searchUserOffered ORDER BY t.createdAt DESC")
    Set<Todo> findTodoByOfferedUserId(@Param("searchUserOffered") long searchUserOffered);


    // LOGIC TO SORT BY STATUS -> NOT IN USE (FASTER TO SORT IN FRONTEND!!)

    default Set<Todo> findTodoByOfferedUserIdSorted(long searchUserOffered) {
        Set<Todo> todos = findTodoByOfferedUserId(searchUserOffered);
        Set<Todo> sortedTodos = todos.stream()
                .sorted(Comparator.comparingInt(todo -> (int) getStatusOrder(todo)))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return sortedTodos;
    }

    private long getStatusOrder(Todo todo) {
        switch (todo.getStatus()) {
            case "In Arbeit": return 1;
            case "Offen": return 2;
            case "Abgelaufen": return 3;
            case "Erledigt": return 4;
            default: return 5; // in case there are unexpected statuses
        }

    }


    @Query("SELECT t FROM Todo t WHERE t.userTaken.userId = :searchUserTaken ORDER BY t.createdAt DESC")
    Set<Todo> findTodoByTakenUserId(@Param("searchUserTaken") long searchUserTaken);

    @Modifying
    @Transactional
    @Query("DELETE FROM Todo t WHERE t.expiresAt < CURRENT_TIMESTAMP")
    void deleteTodosExpired();

    @Modifying
    @Transactional
    @Query("DELETE FROM Todo t WHERE t.expiresAt < :dateTime")
    void deleteTodosExpiredBefore(@Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT t FROM Todo t WHERE t.expiresAt > CURRENT_TIMESTAMP " +
            "AND t.status = 'Offen'" +
            "ORDER BY t.createdAt DESC")
    Set<Todo> findTodosNotExpiredAndOpen();

    @Modifying
    @Transactional
    @Query("UPDATE Todo t SET t.status = 'Abgelaufen' WHERE t.expiresAt < CURRENT_TIMESTAMP")
    void setTodosExpiredStatus();

    @Query("SELECT t FROM Todo t WHERE t.userOffered.address.postalCode = :searchPostalCode " +
            "AND t.expiresAt > CURRENT_TIMESTAMP AND t.status = 'Offen'" +
            "ORDER BY t.createdAt DESC")
    Set<Todo> findTodoByPostalCode(@Param("searchPostalCode") String searchPostalCode);

    @Query("SELECT t FROM Todo t WHERE t.userOffered.address.city = :searchCity " +
            "AND t.expiresAt > CURRENT_TIMESTAMP AND t.status = 'Offen'" +
            "ORDER BY t.createdAt DESC")
    Set<Todo> findTodoByCity(@Param("searchCity") String searchCity);

}

