package com.java.everyboard.comment.repository;

import com.java.everyboard.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select * from comments where content_id = :contentId", nativeQuery = true)
    List<Comment> findAllByContentId(long contentId);

    @Query(value = "select * from comments where user_id = :userId", nativeQuery = true)
    List<Comment> findAllByUserId(long userId);
}