package cn.edu.swpu.cins.openday;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@EnableCaching
public class Main {
	public static void main(String[] args) {
		new SpringApplication(Main.class).run(args);
	}

	@Bean("myRedisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		Jackson2JsonRedisSerializer<Object> jsonRedisSerializer =
						new Jackson2JsonRedisSerializer<>(Object.class);
		jsonRedisSerializer.setObjectMapper(new ObjectMapper()
						.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
						.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL));
		redisTemplate.setValueSerializer(jsonRedisSerializer);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		return redisTemplate;
	}
}
