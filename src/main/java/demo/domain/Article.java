package demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Article {

	@Id
	@GeneratedValue
	private Long id;
	private String subject;
	private String content;
	private int numread;
	@Transient
	private Long numreadRedis;

	public Article() {
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public int getNumread()
	{
		return numread;
	}

	public void setNumread(int numread)
	{
		this.numread = numread;
	}
	

	public Long getNumreadRedis()
	{
		return numreadRedis;
	}

	public void setNumreadRedis(Long numreadRedis)
	{
		this.numreadRedis = numreadRedis;
	}

	@Override
	public String toString()
	{
		return "Article [id=" + id + ", subject=" + subject + ", content=" + content + ", numread=" + numread
				+ ", numreadRedis=" + numreadRedis + "]";
	}

	
}
