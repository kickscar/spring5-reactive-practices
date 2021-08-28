package me.kicksar.reactive.basics.ex04;

import java.util.concurrent.ExecutionException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

/**
 * 
 * 스프링에서의 비동기 처리:
 * 
 * @Async + Callback(ListenableFuture from Spring 4.0)
 * 
 * 장점: Non-Blocking 으로 비동기 처리 결과를 받아 올 수 있다.
 *
 */
public class Ex02App {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext( ContextConfig.class );
		MyService myService = context.getBean( MyService.class );

		myService.service().addCallback(new SuccessCallback<String>() {

			@Override
			public void onSuccess(String result) {
				System.out.println("[Thread-" + Thread.currentThread().getId() +"] success: " + result);
			}
			
		}, new FailureCallback() {
			@Override
			public void onFailure(Throwable ex) {
				System.out.println("[Thread-" + Thread.currentThread().getId() +"] error: " + ex);
			}
		});
		
		for(int i = 0; i < 5; i++) {
			System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업1");
			Thread.sleep(1000);
		}
		System.out.println("[Thread-" + Thread.currentThread().getId() +"] Exit");	
		
		context.close();
	}
	
	
	@Configuration
	@EnableAsync
	public static class ContextConfig {
		@Bean
		MyService myService() {
			return new MyService();
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
		
	}
	
	public static class MyService {
		@Async
		public ListenableFuture<String> service() throws InterruptedException {
			System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업");
			Thread.sleep(5000);
			return new AsyncResult<String>("Hello");
		}
	}
}
