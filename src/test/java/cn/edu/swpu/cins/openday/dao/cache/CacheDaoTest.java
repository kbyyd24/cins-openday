package cn.edu.swpu.cins.openday.dao.cache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CacheDaoTest {

	@Mock
	private RedisTemplate<String, Object> redisTemplate;

	private CacheDao cacheDao;

	@Before
	public void setUp() throws Exception {
		cacheDao = new CacheDao(redisTemplate, 30);
	}

	@Mock
	private BoundHashOperations<String, Object, Object> ops;

	@Test
	public void test_signUp() throws Exception {
		String key = "key";
		HashMap<String, String> hash = new HashMap<>();
		when(redisTemplate.boundHashOps(key)).thenReturn(ops);
		doNothing().when(ops).putAll(hash);
		when(ops.expire(anyLong(), any())).thenReturn(true);
		cacheDao.signUp(key, hash);
		verify(redisTemplate).boundHashOps(key);
		verify(ops).putAll(hash);
		verify(ops).expire(anyLong(), any());
	}

	@Test
	public void test_getEnableToken_return_token() throws Exception {
		String token = "token";
		Object oToken = token;
		String key = "key";
		when(redisTemplate.boundHashOps(key)).thenReturn(ops);
		when(ops.get(eq(token))).thenReturn(oToken);
		when(ops.delete(key)).thenReturn(1L);
		String enableToken = cacheDao.getEnableToken(key);
		assertThat(enableToken, is(token));
		verify(redisTemplate).boundHashOps(key);
		verify(ops).get(eq(token));
		verify(ops).delete(key);
	}

	@Test
	public void test_getEnableToken_return_null() throws Exception {
		String token = "token";
		Object oToken = null;
		String key = "key";
		when(redisTemplate.boundHashOps(key)).thenReturn(ops);
		when(ops.get(eq(token))).thenReturn(oToken);
		when(ops.delete(key)).thenReturn(1L);
		String enableToken = cacheDao.getEnableToken(key);
		assertThat(enableToken, isEmptyOrNullString());
		verify(redisTemplate).boundHashOps(key);
		verify(ops).get(eq(token));
		verify(ops).delete(key);
	}

	@Test
	public void test_signIn() throws Exception {
		String key = "key";
		HashMap<String, String> hash = new HashMap<>();
		when(redisTemplate.boundHashOps(key)).thenReturn(ops);
		doNothing().when(ops).putAll(hash);
		when(ops.expire(anyLong(), any())).thenReturn(true);
		cacheDao.signIn(key, hash);
		verify(redisTemplate).boundHashOps(key);
		verify(ops).putAll(hash);
		verify(ops).expire(anyLong(), any());
	}

	@Test
	public void test_signOut() throws Exception {
		String key = "key";
		doNothing().when(redisTemplate).delete(key);
		cacheDao.signOut(key);
		verify(redisTemplate).delete(key);
	}

	@Test
	public void test_getSignToken_return_token() throws Exception {
		String token = "token";
		Object oToken = token;
		String key = "key";
		when(redisTemplate.boundHashOps(key)).thenReturn(ops);
		when(ops.get(eq(token))).thenReturn(oToken);
		String enableToken = cacheDao.getEnableToken(key);
		assertThat(enableToken, is(token));
		verify(redisTemplate).boundHashOps(key);
		verify(ops).get(eq(token));
	}

	@Test
	public void test_getSignToken_return_null() throws Exception {
		String token = "token";
		Object oToken = null;
		String key = "key";
		when(redisTemplate.boundHashOps(key)).thenReturn(ops);
		when(ops.get(eq(token))).thenReturn(oToken);
		String enableToken = cacheDao.getEnableToken(key);
		assertThat(enableToken, isEmptyOrNullString());
		verify(redisTemplate).boundHashOps(key);
		verify(ops).get(eq(token));
	}
}