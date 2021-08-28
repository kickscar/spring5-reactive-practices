package me.kickscar.java.functional.ch3.ex14;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

public class App {
	
	public static void ex01() {
		File[] hiddenFiles = new File("C:/").listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isHidden();
			}
		});
		
		System.out.println(Arrays.toString(hiddenFiles));
	}
	
	public static void ex02() {
		File[] hiddenFiles = new File("C:/").listFiles(File::isHidden);
		
		System.out.println(Arrays.toString(hiddenFiles));
	}

	public static void main(String[] args) {
		ex01();
		ex02();
	}
}