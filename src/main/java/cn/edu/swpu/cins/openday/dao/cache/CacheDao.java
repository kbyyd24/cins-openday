package cn.edu.swpu.cins.openday.dao.cache;

import org.springframework.stereotype.Repository;

@Repository
public class CacheDao {
	public boolean existField(String key, String field) {
		return false;
	}

	public boolean saveEntry(String key, String field, Object value) {
		return false;
	}
}
