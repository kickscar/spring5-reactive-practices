package me.kicksar.reactive.basics.ex01;

import java.util.Observable;
import java.util.Observer;

/* 
 * GoF Observer Pattern
 *  
 * Problems: by Microsoft Reactive eXtension Team
 * 
 *  1. How to Notify Completion to Observer ?    
 *  2. How to Deal Error or Exception ?
 *  
 */
public class Ex02App {

	public static void main(String[] args) {
		IntObservable intOb = new IntObservable();

		intOb.addObserver( new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				System.out.println( arg );
			}
		});
		
		intOb.run();
	}
	
	public static class IntObservable extends Observable implements Runnable {
		@Override
		public void run() {
			for(int i = 1; i <= 10; i++) {
				setChanged();
				notifyObservers(i);
				
				// Duality
				// notifyObservers(i) <-> Iterator's it.next();
			    // void method(DATA)  <-> DATA method(void)
				// Push               <-> Pull
			}
		}
	}
}