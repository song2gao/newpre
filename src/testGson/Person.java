package testGson;

import java.util.List;

public class Person {

	private String name;    
	private int age;
	private List<Children> childList;
	
	
	public List<Children> getChildList() {
		return childList;
	}
	public void setChildList(List<Children> childList) {
		this.childList = childList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
