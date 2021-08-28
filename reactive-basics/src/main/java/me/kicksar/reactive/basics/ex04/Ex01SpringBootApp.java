package me.kicksar.reactive.basics.ex04;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;


/**
 * 스프링에서의 비동기 처리: 
 * 
 * Ex01App with Spring Boot
 * 
 */

@SpringBootApplication
@EnableAsync
public class Ex01SpringBootApp {
	
	public static void main(String[] args) {
		try(ConfigurableApplicationContext c = SpringApplication.run(Ex01SpringBootApp.class, args)){
		}
	}

	@Autowired
	MyService myService;
	
	@Bean
	ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				Future<String> f = myService.service();
				
				while(f.isDone() == false) {
					System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업1");
					Thread.sleep(1000);
				}
				System.out.println("[Thread-" + Thread.currentThread().getId() +"] " + f.get());
				System.out.println("[Thread-" + Thread.currentThread().getId() +"] Exit");	
			}
		};
	}
	
	@Component
	public static class MyService {
		@Async
		public Future<String> service() throws InterruptedException {
			System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업");
			Thread.sleep(5000);
			return new AsyncResult<String>("Hello");
		}
	}
}