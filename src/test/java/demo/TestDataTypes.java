package demo;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataRedisDemoApplication.class)
public class TestDataTypes {

	private static final String JINQ = "jinq";
	private static final String ELASTIC = "elastic";
	private static final String REDIS = "redis";
	private static final String JPA = "jpa";
	private static final String LECTURE = "lecture";
	private static final String SPRING_DATA = "spring:data";
	private static final String PRESENTERS = "presenters";
	private static final String VALUE_ARAHANSA = "arahansa";
	private static final String KEY_STRING = "redis:test:string";

	@Autowired
	private StringRedisTemplate template;
	
	

	@Test
	public void 문자열타입테스트() throws Exception
	{
		ValueOperations<String, String> ops = this.template.opsForValue();
		// 01. 기본적인 set 해보기
		System.out.println("=== 기본적인 작업(+증가) ====");
		ops.set(KEY_STRING, VALUE_ARAHANSA);
		System.out.println("발견된 키 " + KEY_STRING + ", 값=" + ops.get(KEY_STRING));
		assertEquals(ops.get(KEY_STRING), VALUE_ARAHANSA);

		// 02. append 로 추가해보기
		ops.append(KEY_STRING, ", hi");
		assertEquals(ops.get(KEY_STRING), VALUE_ARAHANSA + ", hi");

		// 03. incr 4 증가해보기
		ops.set("counter", "0");
		System.out.println("증가된 값 :"+ops.increment("counter", 4));
		System.out.println("증가된 값 :"+ops.increment("counter", 2));
		System.out.println("counter:" + ops.get("counter"));
		assertEquals(ops.get("counter"), "6");
		
	}

	@Test
	public void 리스트테스트() throws Exception
	{
		ListOperations<String, String> ops = this.template.opsForList();
		// 01. 키를 일단 지우고, 값을 다시 넣기
		template.delete(PRESENTERS);
		ops.leftPush(PRESENTERS, "아라한사");
		ops.leftPush(PRESENTERS, "holyeye");
		ops.leftPush(PRESENTERS, "arawn");

		// 02. range로 가져오기
		List<String> presenters = ops.range(PRESENTERS, 0, -1);
		assertEquals(3, presenters.size());
		// 03. 직접 여기는 그냥 보자..ㅎ?
		System.out.println("=== 리스트 현재 목록  === ");
		presenters.forEach(n -> System.out.println(n));
		System.out.println("레프트 팝해서 나온 값 :" + ops.leftPop(PRESENTERS));

		// 04. 개수가 2개 확인
		assertEquals(2, ops.range(PRESENTERS, 0, -1).size());
	}

	@Test
	public void 셋테스트해보기() throws Exception
	{
		SetOperations<String, String> ops = this.template.opsForSet();
		String[] arrData = { JPA, REDIS, ELASTIC };
		// 01. 이번엔 가변인자로 데이터를 집어넣자
		ops.add(SPRING_DATA, JPA, REDIS, ELASTIC, JPA);
		Set<String> members = ops.members(SPRING_DATA);
		System.out.println("=== 셋 목록 === ");
		members.forEach(n -> System.out.println(n));

		// 02. jpa, redis, elastic을 가지고 있다!
		for (int i = 0; i < arrData.length; i++) {
			assertTrue(members.contains(arrData[i]));
		}
		// 03. 3 개의 사이즈다 !
		assertEquals(3, members.size());

		// 04. 레디스를 셋에 값으로 가지고 있다
		assertTrue(ops.isMember(SPRING_DATA, REDIS));
	}

	@Test
	public void 정렬셋해보자() throws Exception
	{
		ZSetOperations<String, String> ops = this.template.opsForZSet();

		ops.add(LECTURE, JPA, 1);
		ops.add(LECTURE, JINQ, 3);
		ops.add(LECTURE, REDIS, 2);
		ops.add(LECTURE, ELASTIC, 2.5);
		Set<String> lectures = ops.range(LECTURE, 0, -1);

		System.out.println("=== 정렬셋 세션 목록 ===");
		for (String string : lectures) {
			System.out.println(string);
		}
		Set<TypedTuple<String>> rangeByScoreWithScores = ops.rangeWithScores(LECTURE, 0, -1);
		for (TypedTuple<String> typedTuple : rangeByScoreWithScores) {
			System.out.println("스코어 :"+typedTuple.getScore()+", 값 :"+typedTuple.getValue());
		}
	}
	
	@Test
	public void 해시해보기() throws Exception
	{
		HashOperations<String, Object, Object> ops = this.template.opsForHash();
		ops.put("user:1", "username", "arahansa");
		ops.put("user:1", "email", "arahansa@naver.com");
		ops.put("user:1", "password", "1234");
		
		System.out.println("=== 해시로 값 뽑아내기 === ");
		Map<Object, Object> user = ops.entries("user:1");
		
		for(Map.Entry<Object, Object> e: user.entrySet()){
			System.out.println(e.getKey()+":"+e.getValue());
		}
	}

	// http://arahansa.github.io/docs_spring/redis.html#pipeline
	// 왈도체 번역이지만 뭐..내가 나중에 봐도 내용은 기억이 나니, 뭐 =3=ㅋ
	// 트랜잭션도 이거와 비슷하게 되어있으므로 생략함. 왈도체 레퍼런스에 좀 나와있음^^; 
	@Test
	public void 파이프라이닝() throws Exception
	{
		ListOperations<String, String> ops = this.template.opsForList();
		ops.leftPushAll("myqueue", "hi", "merong", "nihao", "hello");
		int batchsize = 5;
		List<Object> results = this.template.executePipelined(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException
			{
				StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
				for (int i = 0; i < batchsize; i++) {
					stringRedisConn.rPop("myqueue");
				}
				return null;
			}
		});
		System.out.println("====  파이프라이닝 결과 ==== ");
		System.out.println(results);
	}
	
	@Autowired
	JedisConnectionFactory factory;
	// 여기선 template말고 로우하게 해보는 것을 해보기로 함
	// 위의 factory에서 얻어와보자.
	@Test
	public void 로우하게해보기() throws Exception
	{
		JedisConnection conn = factory.getConnection();
		conn.set("rawKey".getBytes(), "rawValue".getBytes());
		byte[] bs = conn.get("rawKey".getBytes());
		assertEquals(new String(bs), "rawValue");
		System.out.println("=== 로우레벨 === ");
		System.out.println("로우레벨 값 :"+new String(bs));
		conn.close();
	}
	
	// 따로 세밀한 설정을 하고 싶을 땐 이렇게 재설정을 해주고 설정파일에 넣어주면 될것같다. 
	// 여기선 세밀한 설정보다는 먼저 익숙해지는데 중점을 두므로, 지금은 스프링부트에 일단 맡기자.(=3=3)
	/*@Bean
	public JedisConnectionFactory factory(){
		JedisConnectionFactory factory = new JedisConnectionFactory();
		JedisPoolConfig poolConfig = factory.getPoolConfig();
		poolConfig.setMaxTotal(20);
		factory.setUsePool(true);
		return factory;
	}*/

}
