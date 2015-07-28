package demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.domain.Article;
import demo.repository.ArticleRepository;
import demo.repository.VisitCounter;

@Service
@Transactional
public class ArticleService {

	@Autowired ArticleRepository repository;
	@Autowired VisitCounter counter; 
	
	public Article getArticleWithJpaHitCount(Long id){
		repository.incrNumRead(id);
		return repository.findOne(id);
	}
	
	public Article getArticleWithRedisCounter(Long id){
		Article article = repository.findOne(id);
		article.setNumreadRedis(counter.addVisit(id.toString()));
		return article;
	}
	
	public void saveArticle(Article article){
		repository.save(article);
	}
	
}
