package me.kicksar.reactive.basics.ex04;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 스프링에서의 비동기 처리: 
 * 
 * @Async Annotation
 * 
 * 앞의 예제와 달라진 점: 비동기 구현(메카니즘, 기술) 코드가 숨었음, 비즈니스 코드만 남았음
 * 단점: 블록킹 방식으로 비동기 처리 결과를 받아옴 ( main thread: servlet 실행 쓰레드, sub thread: MyService의 service() 시, servlet 실행 쓰레드가 블록킹 됨 -> CPU와 메모리 자원 많이 소비 왜? Context Switching!!! )
 *   
 */

public class Ex01App {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext( ContextConfig.class );
		MyService myService = context.getBean( MyService.class );

		Future<String> f = myService.service();
		
		while(f.isDone() == false) {
			System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업1");
			Thread.sleep(1000);
		}
		System.out.println("[Thread-" + Thread.currentThread().getId() +"] " + f.get());
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
		
		/*
		 *  @Async의 쓰레드 풀 기본 정책은  SimpleAsyncTaskExecutor(비추천, 테스트용)
		 *  반드시 ThreadPoolTaskExecutor 빈 등록해서 쓰자!
		 */
		@Bean
		TaskExecutor taskExecutor() {
			ThreadPoolTaskExecutor taskExecutor =  new ThreadPoolTaskExecutor();	// Java Tread Pool 모든 특징/장점을 누릴 수 있음
			taskExecutor.setCorePoolSize(10);										// 쓰레드풀에 생성하는 기본적인 쓰레드 갯수 (런타임중에 조정 가능, JMX) 
			taskExecutor.setMaxPoolSize(100);										// 무조건 꽉 채우지 않음 Queue 가 꽉 찬다음 늘어남
			taskExecutor.setQueueCapacity(200);										// 대기.. (지정하지 않으면 무한대이기 때문에 MaxPoolSize는 의미 없음)
			taskExecutor.setThreadNamePrefix("mythread");
			taskExecutor.initialize();
			
			return taskExecutor;
		}		
	}
	
	public static class MyService {
		@Async
		public Future<String> service() throws InterruptedException {
			System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업2");
			Thread.sleep(5000);
			return new AsyncResult<String>("Hello");
		}
	}
}
