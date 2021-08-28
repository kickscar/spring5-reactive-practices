package me.kicksar.reactive.basics.ex03;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Ex04App 람다식 적용
 * 
 * 
 */
public class Ex04LambdaApp {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newCachedThreadPool();
		
		CallbackFutureTask<String> cft = new CallbackFutureTask<String>(() -> {
			
			System.out.println("[Thread-" + Thread.currentThread().getId() +"] 긴작업2");
			Thread.sleep(5000);
			return "Hello";				
			
		}, t -> System.out.println("[Thread-" + Thread.currentThread().getId() +"] success: " + t), e -> System.out.println("[Thread-" + Thread.currentThread().getId() +"] error: " + e));
		
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
