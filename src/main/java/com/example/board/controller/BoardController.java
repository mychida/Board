package com.example.board.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.board.entity.Post;
import com.example.board.factory.PostFactory;
import com.example.board.repository.PostRepository;
import com.example.board.validation.GroupOrder;

/**
 * 掲示板のフロントコントローラー
 */
@Controller
public class BoardController {
	
	@Autowired
	private PostRepository repository;

	/**
	 * 一覧を表示する
	 * @param model モデル
	 * @return テンプレート
	 */
	@GetMapping("/") 
	public String index(Model model) { 
		model.addAttribute("form", PostFactory.newPost()); 
		model = this.setList(model); 
		model.addAttribute("path", "create");
		return "layout";
	}
	
	/**
	 * 一覧を設定する
	 * @param model モデル
	 * @return 一覧を設定したモデル
	 */
	//これまでの投稿を全部取得してmodelに保存するのはわかるが
	//returnでmodelを返しているのがわからない????
	private Model setList(Model model) {
		List<Post> list = repository.findByDeletedFalseOrderByUpdatedDateDesc();
		model.addAttribute("list", list);
		return model;
	}
	
	/**
	 * 登録する（新規投稿）
	 * @param form フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@PostMapping("/create") //???????
	//BindingResult←エラー時の情報格納用
	public String create(@ModelAttribute("form") @Validated(GroupOrder.class) Post form, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			//savaAndFlushメソッドで、formに格納された投稿内容を保存する
			repository.saveAndFlush(PostFactory.createPost(form));
			//上のsaveAndFlushで保存したのに、また保存してる？？？？
			model.addAttribute("form", PostFactory.newPost());
		}
		//登録した内容を含む一覧をsetListで表示
		model = this.setList(model);
		model.addAttribute("path", "create");
		return "layout";
	}
	
	/**
	 * 編集する投稿を表示する
	 * @param form フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@GetMapping("/edit")
	public String edit(@ModelAttribute("form") Post form, Model model) {
		//編集する投稿を、findByIdメソッドで検索して取り出す
		//form.getId()←どこのメソッド？？？
		Optional<Post> post  = repository.findById(form.getId());
		//取り出した投稿をmodelに保存
		model.addAttribute("form", post);
		model = setList(model);
		model.addAttribute("path", "update");
		return "layout";
	}
	
	/**
	 * 更新する
	 * @param form フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@PostMapping("/update")
	public String update(@ModelAttribute("form") @Validated(GroupOrder.class) Post form, BindingResult result,Model model) {
		if(!result.hasErrors()) {
			//編集する投稿を、findByIdメソッドで取り出す
			//Optional←nullかどうかのチェックを簡潔にする・nullの可能性があることを明示できる
			//form.getId()←どこからきたメソッド？
			Optional<Post> post = repository.findById(form.getId());
			//formに格納された投稿の値を、updatedPostメソッドで上書きしてから、saveAndFlushメソッドで上書き
			//??post.get()は何？idからdeletedまでのgetter?
			repository.saveAndFlush(PostFactory.updatedPost(post.get(), form));
		}
		//newPost?新規投稿？？？？
		model.addAttribute("form", PostFactory.newPost());
		//引数も、メソッドの処理結果を入れる変数もmodel???
		model = setList(model);
		model.addAttribute("path", "create");
		return "layout";
	}
	
	/**
	 * 削除する
	 * @param form フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@GetMapping(value = "/delete")
	public String delete(@ModelAttribute("form") Post form, Model model) {
		//削除する投稿を、findById メソッドで取り出す
		Optional<Post> post = repository.findById(form.getId());
		/**deletedPostメソッドの中の、setDeleted(true)で削除済みの設定をしてから、
		 * saveAndFlushメソッドを呼び出し保存
		 */
		repository.saveAndFlush(PostFactory.deletedPost(post.get()));
		model.addAttribute("form", PostFactory.newPost());
		model = setList(model);
		model.addAttribute("path", "create");
		return "layout";
		
	}
	
}
