//掲示板の投稿データを格納する Bean
package com.example.board.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 投稿
 */
@Entity
@Table(name = "posts")
@Data
public class Post {

	/**ID*/
	@Id
	@Column
	@NotNull
	private String id = null;

	/**投稿者*/
	@Column(length = 20, nullable = false)
	@NotEmpty
	@Size(min = 1, max = 20)
	private String author = null;

	/**タイトル*/
	@Column(length = 20, nullable = false)
	@NotEmpty
	@Size(min = 1, max = 20)
	private String title = null;

	/**内容*/
	@Column(length = 1000, nullable = false)
	@NotEmpty
	@Size(min = 1, max = 1000)
	private String body = null;

	/**登録日時*/
	private Date createdDate = null;

	/**更新日時*/
	private Date updatedDate = null;

	/**削除済み*/
	private boolean deleted = false;
}
