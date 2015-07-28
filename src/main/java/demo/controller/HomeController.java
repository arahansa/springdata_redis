package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.repository.ArticleRepository;


@Controller
public class HomeController {

	@Autowired ArticleRepository repository;
	
	@RequestMapping("/")
	public String home(Model model){
		model.addAttribute("articles", repository.findAll());
		return "index";
	}
}
