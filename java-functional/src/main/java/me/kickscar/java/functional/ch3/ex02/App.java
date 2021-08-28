package me.kickscar.java.functional.ch3.ex02;

public class App {
	
	public static void ex01() {
		String x = "seoul";
		
		String r1 = x.replace('s', 'S');
		String r2 = x.replace('s', 'S');
		
		System.out.println(r1 + " == " + r2);
		
		// x가 지칭하는 표현식으로 치환하기
		r1 = "seoul".replace('s', 'S');
		r2 = "seoul".replace('s', 'S');

		System.out.println(r1 + " == " + r2);
	}
	
	
	public static void ex02() {
		StringBuilder x = new StringBuilder("Hello");
		StringBuilder y = x.append(" World");
		
		String r1 = y.toString();
		String r2 = y.toString();
		
		System.out.println(r1 + " == " + r2);
		
		// y가 지칭하는 표현식으로 치환하기
		r1 = x.append(" World").toString();
		r2 = x.append(" World").toString();

		System.out.println(r1 + " != " + r2);
	}

	public static void main(String[] args) {
		// 참조에 투명한 함수 예제
		ex01();

		// 참조에 투명하지 않은 함수 예제
		ex02();
	}
}