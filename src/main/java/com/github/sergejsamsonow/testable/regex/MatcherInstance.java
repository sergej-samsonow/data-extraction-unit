package com.github.sergejsamsonow.testable.regex;

public class MatcherInstance implements Matcher {

	private java.util.regex.Matcher matcher;

	public MatcherInstance(java.util.regex.Matcher matcher) {
		this.matcher = matcher;
	}

	@Override
	public boolean find() {
		return matcher.find();
	}

	@Override
	public String group(int id) {
		return matcher.group(id);
	}

	@Override
	public String group(String name) {
		return matcher.group(name);
	}

}
