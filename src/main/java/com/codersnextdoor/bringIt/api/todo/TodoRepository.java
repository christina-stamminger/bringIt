package com.codersnextdoor.bringIt.api.todo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE t.userOffered.userId = :searchUserOffered")
    Set<Todo> findTodoByOfferedUserId(@Param("searchUserOffered") long searchUserOffered);

    @Query("SELECT t FROM Todo t WHERE t.userTaken.userId = :searchUserTaken")
    Set<Todo> findTodoByTakenUserId(@Param("searchUserTaken") long searchUserTaken);
}
