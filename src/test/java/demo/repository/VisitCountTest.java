package demo.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.SpringDataRedisDemoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataRedisDemoApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VisitCountTest {
	
	@Autowired VisitCounter counter;
	
	@Test
	public void a_testAddVisit() throws Exception
	{
		assertTrue(counter.addVisit("52") > 0);
		assertTrue(counter.addVisit("180") > 0);
		assertTrue(counter.addVisit("554") > 0);
	}
	
	@Test
	public void b_testGetVisitCount() throws Exception
	{
		List<String> result = counter.getVisitListCount("52", "180", "554");
		assertNotNull(result);
		assertTrue(result.size() == 3);
		
		long sum= 0;
		for(String count: result){
			sum = sum+Long.parseLong(count);
		}
		String totalCount = counter.getVisitTotalCount();
		assertEquals(String.valueOf(sum), totalCount);
	}

}
