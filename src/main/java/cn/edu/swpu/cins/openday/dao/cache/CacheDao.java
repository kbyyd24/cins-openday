package cn.edu.swpu.cins.openday.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CacheDao {
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	public CacheDao(RedisTemplate<String, String> stringRedisTemplate) {
		this.redisTemplate = stringRedisTemplate;
	}

	@Transactional
	public boolean saveEntry(String key, String field, Object value) {
		redisTemplate.opsForHash().putIfAbsent(key, field, value);
		// TODO: 16-10-25 处理失败时的验证
		return true;
	}

	public Object getValue(String key, String mail) {
		return redisTemplate.opsForHash().get(key, mail);
	}

	public long removeValue(String key, String mail) {
		return redisTemplate.opsForHash().delete(key, mail);
	}
}
