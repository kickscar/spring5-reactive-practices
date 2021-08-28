package me.kickscar.java.functional.ch3.ex01;

public class Apple {
	private Integer weight;
	
	public Apple(int weight) {
		this.weight = weight;
	}
	
	public Integer getWeight() {
		return weight;
	}
	
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Apple [weight=" + weight + "]";
	}	
}
