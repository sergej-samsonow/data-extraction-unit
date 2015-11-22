package com.github.sergejsamsonow.testable.regex;

public interface Matcher {

	public boolean find();
	public String group(int id);
	public String group(String name);
}
