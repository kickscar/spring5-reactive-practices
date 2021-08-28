package me.kicksar.reactive.basics.ex04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@SpringBootApplication
@EnableAsync
public class Ex02LambdaSpringBootApp {
	public static void main(String[] args) {
		try(ConfigurableApplicationContext c = SpringApplication.run(Ex01SpringBootApp.class, args)){
		}
	}

	@Autowired
	MyService myService;
	
	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			
			myService.service().addCallback( 
					result -> System.out.println("[Thread-" + Thread.currentThread().getId() +"] success: " + result), 
					ex -> System.out.println("[Thread-" + Thread.currentThread().getId() +"] error: " + ex));
			
			for(int i = 0; i < 5; i++) {
				System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업1");
				Thread.sleep(1000);
			}
			System.out.println("[Thread-" + Thread.currentThread().getId() +"] Exit");	

		};
	}
	
	@Bean
	TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor =  new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(10);  
		taskExecutor.setMaxPoolSize(100);
		taskExecutor.setQueueCapacity(200);
		taskExecutor.setThreadNamePrefix("mythread");
		taskExecutor.initialize();
		
		return taskExecutor;
	}
	
	@Component
	public static class MyService {
		@Async
		public ListenableFuture<String> service() throws InterruptedException {
			System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업2");
			Thread.sleep(5000);
			return new AsyncResult<String>("Hello");
		}
	}
}
