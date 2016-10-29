package cn.edu.swpu.cins.openday.dao.cache;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.core.RedisTemplate;

@RunWith(MockitoJUnitRunner.class)
public class CacheDaoTest {

	@Mock
	private RedisTemplate<String, String> redisTemplate;

	private CacheDao cacheDao;

	@Before
	public void setUp() throws Exception {
		cacheDao = new CacheDao(redisTemplate, 30);
	}

}