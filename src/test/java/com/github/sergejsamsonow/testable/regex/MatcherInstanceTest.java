package com.github.sergejsamsonow.testable.regex;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.regex.Pattern;

import org.junit.Test;

public class MatcherInstanceTest {

	@Test
	public void findTrue() throws Exception {
		Matcher m = matcher("(.*)", "BLA");
		assertThat(m.find(), equalTo(true));
	}

	@Test
	public void findFalse() throws Exception {
		Matcher m = matcher("(C+)", "BLA");
		assertThat(m.find(), equalTo(false));
	}

	@Test
	public void groupOne() throws Exception {
		Matcher m = matcher("(.*)", "BLA");
		m.find();
		assertThat(m.group(1), equalTo("BLA"));
	}

	@Test
	public void groupWord() throws Exception {
		Matcher m = matcher("(?<CONTENT>.*)", "BLA");
		m.find();
		assertThat(m.group("CONTENT"), equalTo("BLA"));
	}

	private Matcher matcher(String regex, String content) {
		return new MatcherInstance(Pattern.compile(regex).matcher(content));
	}


}
