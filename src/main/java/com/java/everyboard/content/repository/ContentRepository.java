package com.java.everyboard.content.repository;

import com.java.everyboard.constant.Category;
import com.java.everyboard.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findAllByCategory(Category category);

    @Query(value = "select * from contents where user_id = :userId", nativeQuery = true)
    List<Content> findAllByUserId(long userId);

    // 게시글 조회수 상위 조회//
    @Query(value = "select * from contents order by view_count desc limit 10", nativeQuery = true)
    List<Content> findContentsViewRank();
    @Query(value = "select * from contents WHERE title LIKE %:keyword% OR content LIKE %:keyword% ", nativeQuery = true)
    List<Content> findAllSearch(@Param(value = "keyword")String keyword);
}