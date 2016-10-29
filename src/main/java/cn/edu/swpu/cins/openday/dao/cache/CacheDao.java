package cn.edu.swpu.cins.openday.dao.cache;

import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Repository
public class CacheDao {
	private RedisTemplate<String, String> redisTemplate;
	private long expireTime;

	@Autowired
	public CacheDao(RedisTemplate<String, String> stringRedisTemplate,
	                @Value("${openday.redis.expire}:30") int expireTime) {
		this.redisTemplate = stringRedisTemplate;
		this.expireTime = expireTime;
	}

	@Deprecated
	public boolean saveEntry(String key, String field, Object value) {
		redisTemplate.opsForHash().putIfAbsent(key, field, value);
		// TODO: 16-10-25 处理失败时的验证
		return true;
	}

	@Deprecated
	public Object getValue(String key, String mail) {
		return redisTemplate.opsForHash().get(key, mail);
	}

	@Deprecated
	public long removeValue(String key, String mail) {
		return redisTemplate.opsForHash().delete(key, mail);
	}

	@Deprecated
	public boolean signIn(UserSignInResult signInResult) {
		return false;
	}

	public void signUp(String key, HashMap<String, String> hash) {
		BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(key);
		ops.putAll(hash);
		ops.expire(expireTime, TimeUnit.MINUTES);
	}

	public String getEnableToken(String key) {
		BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(key);
		Object token = ops.get("token");
		ops.delete(key);
		if (token == null) {
			return "";
		}
		return (String) token;
	}

	public void signIn(String key, HashMap<String, String> map) {
		BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(key);
		ops.putAll(map);
		ops.expire(expireTime, TimeUnit.DAYS);
	}

	public void signOut(String key) {
		redisTemplate.boundHashOps(key).expire(expireTime, TimeUnit.MICROSECONDS);
	}
}
