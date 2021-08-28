package me.kicksar.reactive.basics.ex03;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 비동기 작업 결과를 가져오는 방법2:
 * 
 * FutureTask를 사용한 callback 방식.
 * callback 같은 기법을 사용할 수 있는 매카니즘이 구현되어 있다.
 * 
 */

public class Ex03App {

	// Future와 같은 방식도 지원
	public static void ex01() throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newCachedThreadPool();

		FutureTask<String> ft = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업2");
				Thread.sleep(5000);
				return "Hello";				
			}
		});
		
		es.execute(ft);
		
		while( ft.isDone() == false ) {
			System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업1");
			Thread.sleep(1000);
		}
		System.out.println("[Thread-" + Thread.currentThread().getId() +"] " + ft.get());
		System.out.println("[Thread-" + Thread.currentThread().getId() +"] Exit");

		es.shutdown();
	}
	
	
	// done() 을 오버라이딩 하여 논블록킹과 비동기가 분명한 코드를 작성할 수 있다.
	// isDone() 으로 비동기 작업의 상태를 확인하지 않아도 되기 때문에 비동기 작업 결과를 받는 작업은 비동기 논블록킹이 된다.
	public static void ex02() throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newCachedThreadPool();

		FutureTask<String> ft = (new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업2");
				Thread.sleep(5000);
				return "Hello";				
			}
		}){
			@Override
			protected void done() {
				super.done();
				try {
					System.out.println("[Thread-" + Thread.currentThread().getId() +"] " + get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		});
		
		es.execute(ft);
		es.shutdown();
		
		for(int i = 0; i < 5; i++) {
			System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업1");
			Thread.sleep(1000);
		}
		System.out.println("[Thread-" + Thread.currentThread().getId() +"] Exit");
	}
	

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// ex01();
		ex02();
	}	
}