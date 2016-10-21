package cn.edu.swpu.cins.openday.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

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

	public boolean saveEntry(String key, String field, Object value) {
		return false;
	}
}
