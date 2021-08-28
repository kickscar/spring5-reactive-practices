package me.kickscar.java.functional.ch3.ex08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.kickscar.java.functional.ch3.ex07.Apple;
import me.kickscar.java.functional.ch3.ex07.ApplePredicate;

/* 동작 파라미터화 기법 중 익명 클래스를  사용한 기법 */
public class App {
	public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p /* 인터페이스 인수를 통해 한개의 파라미터로 다양한 동작을 수행할 수 있다.*/ ) {
		List<Apple> result = new ArrayList<Apple>();
		
		for (Apple apple : inventory) {
			if(p.test(apple)) { // 프레디케이트 객체로 사과 검사 조건을 캡슐화 했다.
				result.add(apple);
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		List<Apple> inventory = Arrays.asList(new Apple[] { 
			new Apple(100, "red"),
			new Apple(300, "green"),
			new Apple(200, "red"),
			new Apple(500, "green")
		});

		List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
			@Override
			public boolean test(Apple apple) {
				return "red".equals(apple.getColor());
			}
		});
		
		List<Apple> heavyApples = filterApples(inventory, new ApplePredicate() {
			@Override
			public boolean test(Apple apple) {
				return apple.getWeight() > 150;
			}
		});

		List<Apple> greenHeavyApples = filterApples(inventory, new ApplePredicate() {
			@Override
			public boolean test(Apple apple) {
				return "green".equals(apple.getColor()) && apple.getWeight() > 150;
			}
		});
		
		System.out.println(redApples);
		System.out.println(heavyApples);
		System.out.println(greenHeavyApples);
	}
}
