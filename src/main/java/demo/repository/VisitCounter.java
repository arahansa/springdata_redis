package demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VisitCounter {

	
	private static final String KEY_EVENT_CLICK_TOTAL = "event:click:total";
	private static final String KEY_EVENT_CLICK = "event:click:";
	
	@Autowired
	private StringRedisTemplate template;
	
	/**
	 * 전체 이벤트 페이지 숫자 하나 증가시키고(주석)
	 * @param eventId
	 * @return 현재 아이디에 해당하는 숫자 증가 후 반환
	 */
	public Long addVisit(String eventId){
		//template.opsForValue().increment(KEY_EVENT_CLICK_TOTAL, 1);
		return template.opsForValue().increment(KEY_EVENT_CLICK+eventId, 1);
	}
	
	public String getVisitTotalCount(){
		return template.opsForValue().get(KEY_EVENT_CLICK_TOTAL);
	}
	
	public List<String> getVisitListCount(String... eventList){
		List<String> result = new ArrayList<String>();
		for(int i=0;i<eventList.length;i++){
			result.add(template.opsForValue().get(KEY_EVENT_CLICK+eventList[i]));
		}
		return result;
	}
}
