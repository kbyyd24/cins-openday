package cn.edu.swpu.cins.openday.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CacheDao {
	private RedisTemplate<Object, Object> redisTemplate;

	@Autowired
	public CacheDao(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public boolean existField(String key, String field) {
		return redisTemplate.opsForHash().hasKey(key, field);
	}

	@Transactional
	public boolean saveEntry(String key, String field, Object value) {
		redisTemplate.opsForHash().put(key, field, value);
		return true;
	}
}
