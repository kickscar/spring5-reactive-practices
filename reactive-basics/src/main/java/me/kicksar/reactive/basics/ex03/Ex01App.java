package me.kicksar.reactive.basics.ex03;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  Future 의 기본 개념
 * 
 */
public class Ex01App {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newCachedThreadPool();
		
		es.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					System.out.println("[Thread-" + Thread.currentThread().getId() +"] Hello");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		System.out.println("[Thread-" + Thread.currentThread().getId() +"] Exit");

		es.shutdown();
	}	
}
