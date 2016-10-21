package cn.edu.swpu.cins.openday.dao.cache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CacheDaoTest {

	@Mock
	private RedisTemplate<Object, Object> redisTemplate;

	private CacheDao cacheDao;

	@Before
	public void setUp() throws Exception {
		cacheDao = new CacheDao(redisTemplate);
	}

	String key = "authentication";
	String field = "mail@mail.com";

	@Test
	public void test_existField_return_false() throws Exception {
		HashOperations<Object, Object, Object> mockOps = Mockito.mock(HashOperations.class);
		when(redisTemplate.opsForHash()).thenReturn(mockOps);
		when(mockOps.hasKey(key, field)).thenReturn(false);
		boolean ret = cacheDao.existField(key, field);
		assertFalse(ret);
	}

	@Test
	public void test_existField_return_true() throws Exception {
		HashOperations<Object, Object, Object> mockOps = Mockito.mock(HashOperations.class);
		when(redisTemplate.opsForHash()).thenReturn(mockOps);
		when(mockOps.hasKey(key, field)).thenReturn(true);
		boolean ret = cacheDao.existField(key, field);
		assertTrue(ret);
	}
}