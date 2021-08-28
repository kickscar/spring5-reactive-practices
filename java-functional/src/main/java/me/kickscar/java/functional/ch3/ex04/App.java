package me.kickscar.java.functional.ch3.ex04;

import java.util.Optional;

public class App {

	public static void main(String[] args) {
		Optional<Integer> i = OptionalUtils.stringToInt("");
		System.out.println(i.isPresent());
	}
}