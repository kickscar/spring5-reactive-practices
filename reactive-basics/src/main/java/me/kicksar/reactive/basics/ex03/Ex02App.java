package me.kicksar.reactive.basics.ex03;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 비동기 작업 결과를 가져오는 방법1:
 * 
 * Future와 같은 핸들러(객체) 반환
 * 
 */
public class Ex02App {
	
	// get 메서드 사용
	public static void ex01() throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newCachedThreadPool();
		
		Future<String> f = es.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업1");
				Thread.sleep(3000);
				return "Hello";
			}
			
		});
		
		System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업2");
		Thread.sleep(2000);
		System.out.println("[Thread-" + Thread.currentThread().getId() +"] Exit");

		System.out.println("[Thread-" + Thread.currentThread().getId() +"] " + f.get());  // Blocking!!!

		es.shutdown();
	}
	
	
	// Future 객체의 isDone을 사용하여 루프에서
	// 비동기 작업의 상태를 계속 확인하면서 논블록킹 처럼 돌게할 순 있다.
	public static void ex02() throws InterruptedException, ExecutionException {

		ExecutorService es = Executors.newCachedThreadPool();
		
		Future<String> f = es.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업2");
				Thread.sleep(5000);
				return "Hello";
			}
		});

		while(f.isDone() == false) {
			System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업1");
			Thread.sleep(1000);
		}
		System.out.println("[Thread-" + Thread.currentThread().getId() +"] " + f.get());
		System.out.println("[Thread-" + Thread.currentThread().getId() +"] Exit");
		
		es.shutdown();
	}
	
	

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// ex01();
		ex02();
	}	
}
