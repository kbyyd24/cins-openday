package cn.edu.swpu.cins.openday.dao.cache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CacheDaoTest {

	@Mock
	private RedisTemplate<String, String> redisTemplate;

	private CacheDao cacheDao;

	@Before
	public void setUp() throws Exception {
		cacheDao = new CacheDao(redisTemplate);
	}

	@Test
	public void test_saveEntry_return_true() throws Exception {
		String key = "authentication";
		String field = "mail@mail.com";
		HashOperations<String, Object, Object> mockOps = Mockito.mock(HashOperations.class);
		when(redisTemplate.opsForHash()).thenReturn(mockOps);
		String token = "123";
		when(mockOps.putIfAbsent(key, field, token)).thenReturn(true);
		boolean ret = cacheDao.saveEntry(key, field, token);
		verify(redisTemplate).opsForHash();
		verify(mockOps).putIfAbsent(key, field, token);
		assertTrue(ret);
	}
}