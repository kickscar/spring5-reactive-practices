package me.kickscar.java.functional.ch3.ex10;

import java.util.Comparator;


public class App {

	public static void ex01() {
		Comparator<Apple> byWeight = new Comparator<Apple>() {
			@Override
			public int compare(Apple arg0, Apple arg1) {
				return arg0.getWeight().compareTo(arg1.getWeight());
			}
		};
		
		System.out.println(byWeight.compare(new Apple(300, "red"), new Apple(200, "red")));
	}
	
	public static void ex02() {
		Comparator<Apple> byWeight = (Apple arg0, Apple arg1) -> arg0.getWeight().compareTo(arg1.getWeight());
		
		System.out.println(byWeight.compare(new Apple(300, "red"), new Apple(200, "red")));
	}
	
	public static void main(String[] args) {
		ex01();
		ex02();
	}
}
