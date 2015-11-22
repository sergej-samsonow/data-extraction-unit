package com.github.sergejsamsonow.testable.regex;

public class PatternInstance implements Pattern {
	private java.util.regex.Pattern pattern;

	public PatternInstance() {}

	public PatternInstance(String pattern) {
		compile(pattern);
	}

	public PatternInstance(String pattern, int flags) {
		compile(pattern, flags);
	}

	@Override
	public Pattern compile(String regex) {
		pattern = java.util.regex.Pattern.compile(regex);
		return this;
	}

	@Override
	public Pattern compile(String regex, int flags) {
		pattern = java.util.regex.Pattern.compile(regex, flags);
		return this;
	}

	@Override
	public Matcher matcher(String content) {
		if (pattern == null) {
			throw new RuntimeException(
				"Init instance at first with [compile] or costructor method");
		}
		return new MatcherInstance(pattern.matcher(content));
	}

}
