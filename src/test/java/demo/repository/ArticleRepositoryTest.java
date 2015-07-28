package demo.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import demo.SpringDataRedisDemoApplication;
import demo.domain.Article;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataRedisDemoApplication.class)
public class ArticleRepositoryTest {
	
	@Autowired ArticleRepository repository;
	
	@Test
	@Transactional
	public void 저장하고불러와보기() throws Exception
	{
		Article article = new Article();
		article.setContent("안녕하세요");
		article.setSubject("하이");
		repository.save(article);
		assertEquals(1, repository.count());
		repository.incrNumRead(1L);
		
		System.out.println(article);
		Article getArticle = repository.findOne(article.getId());
		System.out.println(getArticle);
	}

}
