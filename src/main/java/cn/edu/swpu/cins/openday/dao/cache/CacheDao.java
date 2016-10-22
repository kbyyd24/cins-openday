package cn.edu.swpu.cins.openday.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CacheDao {
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	public CacheDao(RedisTemplate<String, Object> myRedisTemplate) {
		this.redisTemplate = myRedisTemplate;
	}

	@Transactional
	public boolean saveEntry(String key, String field, Object value) {
		redisTemplate.opsForHash().putIfAbsent(key, field, value);
		return true;
	}
}
