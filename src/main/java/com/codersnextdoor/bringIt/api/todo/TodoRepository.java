package com.codersnextdoor.bringIt.api.todo;


import com.codersnextdoor.bringIt.api.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByUserOfferedAndLocationAndTitleAndDescriptionAndAddInfoAndExpiresAt (User userOffered, String location, String title, String description, String addInfo, LocalDateTime expiresAt);

    @Query("SELECT t FROM Todo t WHERE t.userOffered.userId = :searchUserOffered")
    Set<Todo> findTodoByOfferedUserId(@Param("searchUserOffered") long searchUserOffered);

    @Query("SELECT t FROM Todo t WHERE t.userTaken.userId = :searchUserTaken")
    Set<Todo> findTodoByTakenUserId(@Param("searchUserTaken") long searchUserTaken);

    @Modifying
    @Transactional
    @Query("DELETE FROM Todo t WHERE t.expiresAt < CURRENT_TIMESTAMP")
    void deleteTodosExpired();

    @Modifying
    @Transactional
    @Query("DELETE FROM Todo t WHERE t.expiresAt < :dateTime")
    void deleteTodosExpiredBefore(@Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT t FROM Todo t WHERE t.expiresAt > CURRENT_TIMESTAMP")
    Set<Todo> findTodosNotExpired();

    @Modifying
    @Transactional
    @Query("UPDATE Todo t SET t.status = 'Abgelaufen' WHERE t.expiresAt < CURRENT_TIMESTAMP")
    void setTodosExpiredStatus();

    @Query("SELECT t FROM Todo t WHERE t.userOffered.address.postalCode = :searchPostalCode AND t.expiresAt > CURRENT_TIMESTAMP")
    Set<Todo> findTodoByPostalCode(@Param("searchPostalCode") String searchPostalCode);

    @Query("SELECT t FROM Todo t WHERE t.userOffered.address.city = :searchCity AND t.expiresAt > CURRENT_TIMESTAMP")
    Set<Todo> findTodoByCity(@Param("searchCity") String searchCity);

}
