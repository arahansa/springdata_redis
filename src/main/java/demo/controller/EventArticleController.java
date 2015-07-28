package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.domain.Article;
import demo.repository.ArticleRepository;
import demo.service.ArticleService;

@Controller
@RequestMapping("/event")
public class EventArticleController {

	private static final String INDEX = "index";
	// 편의상을 위해 그냥 붙여놓는다.
	@Autowired ArticleService service;
	@Autowired ArticleRepository repository;
	
	@RequestMapping("/jpa/{id}")
	public String jpa(@PathVariable Long id, Model model)
	{
		Article article = service.getArticleWithJpaHitCount(id);
		model.addAttribute("article", article);
		return INDEX;
	}
	
	@RequestMapping("/redis/{id}")
	public String redis(@PathVariable Long id, Model model)
	{
		Article article = service.getArticleWithRedisCounter(id);
		model.addAttribute("article", article);
		return INDEX;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String post(Article article, Model model){
		service.saveArticle(article);
		model.addAttribute("articles", repository.findAll());
		return INDEX;
	}
	
}
