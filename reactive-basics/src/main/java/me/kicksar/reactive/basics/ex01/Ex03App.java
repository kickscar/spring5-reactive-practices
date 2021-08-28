package me.kicksar.reactive.basics.ex01;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* 비동기 */
public class Ex03App {

	public static void main(String[] args) {
		IntObservable intOb = new IntObservable();
		intOb.addObserver( new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				System.out.println( Thread.currentThread().getId() + ": " + arg );
			}
		});
		
		intOb.run();
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(intOb);
		
		System.out.println( Thread.currentThread().getId() + ": Exits" );
		es.shutdown();
	}
	
	public static class IntObservable extends Observable implements Runnable {
		@Override
		public void run() {
			for(int i = 1; i <= 10; i++) {
				setChanged();
				notifyObservers(i);
			}
		}
	}
}