package com.github.sergejsamsonow.testable.regex;

public interface Pattern {

	public Pattern compile(String regex);
	public Pattern compile(String regex, int flags);
	public Matcher matcher(String content);
}
