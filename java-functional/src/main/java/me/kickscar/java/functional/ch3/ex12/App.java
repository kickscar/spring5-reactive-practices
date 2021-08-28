package me.kickscar.java.functional.ch3.ex12;

public class App {
	
	public static void process(Runnable r) {
		r.run();
	}
	
	public static void main(String[] args) {
		
		Runnable r1 = new Runnable() {									// 1. 익명클래스
			@Override
			public void run() {
				System.out.println("Hello World1");
			}
		};
		
		Runnable r2 = () -> System.out.println("Hello World2");			// 2. 람다식
	
		process(r1);
		process(r2);
		process(() -> System.out.println("Hello World3"));				// 3. 전달된 람다 표현식
	}
}