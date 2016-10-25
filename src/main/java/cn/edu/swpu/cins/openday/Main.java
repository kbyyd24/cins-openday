package cn.edu.swpu.cins.openday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Main {
	public static void main(String[] args) {
		new SpringApplication(Main.class).run(args);
	}

//	@Bean("myRedisTemplate")
//	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(connectionFactory);
//		Jackson2JsonRedisSerializer<Object> jsonRedisSerializer =
//						new Jackson2JsonRedisSerializer<>(Object.class);
//		jsonRedisSerializer.setObjectMapper(new ObjectMapper()
//						.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
//						.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL));
//		redisTemplate.setValueSerializer(jsonRedisSerializer);
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		return redisTemplate;
//	}
}
