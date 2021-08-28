package me.kickscar.java.functional.ch3.ex03;

import java.util.Arrays;
import java.util.stream.IntStream;

public class App {

	public static Integer[] absArray(Integer[] src){
		
		Integer[] dest = new Integer[src.length];
		
		IntStream.range(0, src.length).forEach(idx -> {
			dest[idx] = Math.abs(src[idx]);
		});
		
		return dest;
	}	

	public static void main(String[] args) {
		Integer[] numbers = {-1, 2, -3, 4, -5};
		
		Integer[] r1 = absArray(numbers);
		Integer[] r2 = absArray(numbers);
		
		System.out.println(Arrays.toString(r1));
		System.out.println(Arrays.toString(r2));
	}	
}