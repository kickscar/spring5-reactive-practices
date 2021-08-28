package me.kickscar.java.functional.ch3.ex01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class App {
	
	public static void testImperative(List<Apple> listApple) {
		int countApple = listApple.size();
		
		if(countApple == 0) {
			System.out.println("The Heaviest Weight: 0");
		    return;			
		}

		Apple heaviestApple = listApple.get(0);
		for(Apple a : listApple.subList(1, countApple)) {
		     if(a.getWeight() > heaviestApple.getWeight()) {
		         heaviestApple = a;
		     }
		}
		
		System.out.println("The Heaviest Weight: " + heaviestApple.getWeight());
	}
	
	public static void testDeclarative(List<Apple> listApple) {
		Optional<Apple> heaviestApple = listApple.stream().max(Comparator.comparing(Apple::getWeight));
		System.out.println("The Heaviest Weight: " + heaviestApple.orElse(new Apple(0)).getWeight());
	}

	public static void main(String[] args) {
		List<Apple> inventory = Arrays.asList( new Apple[] {
			new Apple(10),
			new Apple(20),
			new Apple(50),
			new Apple(30)
		});
		
		testImperative(inventory);
		testDeclarative(inventory);
	}
}