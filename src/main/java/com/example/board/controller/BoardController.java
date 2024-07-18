package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.board.factory.PostFactory;

/**
 * 掲示板のコントローラー
 */
@Controller
public class BoardController {

	/**
	 * 一覧を表示する
	 * @param model モデル
	 * @return テンプレート
	 */
	@GetMapping("/") // layoutからのgetリクエストを受け取る？
	public String index(Model model) { //model?
		model.addAttribute("form", PostFactory.newPost()); //form ←キーワード
		return "layout";
	}

}
