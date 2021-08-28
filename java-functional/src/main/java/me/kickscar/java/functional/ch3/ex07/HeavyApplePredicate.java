package me.kickscar.java.functional.ch3.ex07;

public class HeavyApplePredicate implements ApplePredicate {

	@Override
	public boolean test(Apple apple) {
		return apple.getWeight() > 150;
	}

}
