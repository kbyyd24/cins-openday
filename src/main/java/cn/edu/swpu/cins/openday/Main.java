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
}
