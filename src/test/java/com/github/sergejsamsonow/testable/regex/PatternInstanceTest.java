package com.github.sergejsamsonow.testable.regex;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
public class PatternInstanceTest {
	
	private Pattern pattern;
	
	@Before
	public void prepare() {
		pattern = new PatternInstance();
	}

	@Test
	public void compileString() throws Exception {
		assertThat(pattern.compile(".*"), not(nullValue()));
	}

	@Test
	public void compileStringAndFlags() throws Exception {
		assertThat(pattern.compile(".*", java.util.regex.Pattern.DOTALL), not(nullValue()));
	}

	@Test(expected = RuntimeException.class)
	public void matcherOnNotCompiledPattern() throws Exception {
		pattern.matcher("BLA");

	}

	@Test
	public void matcherReturn() throws Exception {
		pattern.compile(".*");
		assertThat(pattern.matcher("BLA"), not(nullValue()));
	}

	@Test
	public void patternFromConstructor() throws Exception {
		pattern = new PatternInstance(".*");
		assertThat(pattern.matcher("BLA"), not(nullValue()));
	}
}
