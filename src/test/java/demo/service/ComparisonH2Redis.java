package demo.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.SpringDataRedisDemoApplication;
import demo.domain.Article;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataRedisDemoApplication.class)
public class ComparisonH2Redis {

	@Autowired ArticleService service;
	
	@Test
	public void 속도비교() throws Exception
	{
		Article article = new Article();
		article.setContent("안녕하세요");
		article.setSubject("하이");
		service.saveArticle(article);
		
		Long id = article.getId();
		
		
		long start = System.currentTimeMillis();
		for(int i=0;i<1000;i++)
			service.getArticleWithJpaHitCount(id);
		long end = System.currentTimeMillis();
		double h2 =  ( end - start )/1000.0;
		System.out.println( "실행 시간 : " + h2);
		
		start = System.currentTimeMillis();
		for(int i=0;i<1000;i++)
			service.getArticleWithRedisCounter(id);
		end = System.currentTimeMillis();
		double redis = ( end - start )/1000.0;
		System.out.println( "실행 시간 : " + redis );
		
	}
	
}
