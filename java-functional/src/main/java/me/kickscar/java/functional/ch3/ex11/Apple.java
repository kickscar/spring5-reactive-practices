package me.kickscar.java.functional.ch3.ex11;

public class Apple {
	private Integer weight;
	private String color;

	public Apple(int weight) {
		this.weight = weight;
	}
	
	public Apple(int weight, String color) {
		this(weight);
		this.color = color;
	}
	
	public Integer getWeight() {
		return weight;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Apple [weight=" + weight + ", color=" + color + "]";
	}
}