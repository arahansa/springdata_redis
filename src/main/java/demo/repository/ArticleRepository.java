package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import demo.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	
	@Modifying(clearAutomatically=true)
	@Query("update Article t set t.numread = t.numread + 1 where t.id = ?1")
	int incrNumRead(Long id);
}
