package com.project.bddsimple.domain.tier;

import com.project.bddsimple.domain.ValueObject;

public class Age implements ValueObject {
	private Integer age;

	public Age(Integer age) {
		super();
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

	public boolean isMineur() {
		return age < 19;
	}
	
	public boolean isMajeur() {
		return age >= 19;
	}
	
	
	
	
}
