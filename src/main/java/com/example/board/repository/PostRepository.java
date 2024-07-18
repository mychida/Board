package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.entity.Post;

/**
 * 投稿のリポジトリー
 */
public interface PostRepository extends JpaRepository<Post, String> {

}
