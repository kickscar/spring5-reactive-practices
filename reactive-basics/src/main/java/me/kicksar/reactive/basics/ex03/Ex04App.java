package me.kicksar.reactive.basics.ex03;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


/**
 * FutureTask의 callback 처리 매카니즘을 코드로 분명하게 보이도록 구현
 * 
 * 1.interface SuccessCallback, FailureCallback 및  CallbackFutureTask 상속 구현 -> Java 7까지는 비스무리한게 없었다.(10년전 활용 코드다)
 * 2.Example Class in JDK : AsynchronousByChannel 
 * 3.단점: 비동기 구현(메카니즘, 기술) 코드 와 비즈니스 코드가 묶여 있음
 */
public class Ex04App {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newCachedThreadPool();
		
		CallbackFutureTask<String> cft = new CallbackFutureTask<String>(new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업2");
				Thread.sleep(5000);
				return "Hello";				
			}
			
		}, new SuccessCallback<String>() {

			@Override
			public void onSuccess(String t) {
				System.out.println("[Thread-" + Thread.currentThread().getId() +"] success: " + t);
			}
			
		}, new FailureCallback() {

			@Override
			public void onFailure(Throwable ex) {
				System.out.println("[Thread-" + Thread.currentThread().getId() +"] error: " + ex);
			}
			
		});
		
		es.execute(cft);
		es.shutdown();		
	
		for(int i = 0; i < 5; i++) {
			System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업1");
			Thread.sleep(1000);
		}
		System.out.println("[Thread-" + Thread.currentThread().getId() +"] Exit");	
	}

	
	
	public static interface SuccessCallback<T> {
		void onSuccess(T t);
	}

	public static interface FailureCallback {
		void onFailure(Throwable ex);
	}
	
	private static class CallbackFutureTask<T> extends FutureTask<T>{
		private SuccessCallback<T> successCallback;
		private FailureCallback failureCallback;
		
		public CallbackFutureTask(Callable<T> callable, SuccessCallback<T> successCallback, FailureCallback failureCallback) {
			super(callable);
			this.successCallback = successCallback;
			this.failureCallback = failureCallback;
		}

		@Override
		protected void done() {
			super.done();
			try {
				successCallback.onSuccess(get());	
			} catch (InterruptedException e) {
				// Processing Interrupt Signal
				Thread.currentThread().interrupt(); 
			} catch (ExecutionException e) {
				failureCallback.onFailure(e);
			}
		}
	}	
}
