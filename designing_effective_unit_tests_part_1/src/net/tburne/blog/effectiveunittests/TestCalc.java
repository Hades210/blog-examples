package net.tburne.blog.effectiveunittests;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCalc {

	@Test
	public void testCalc(){
		assertEquals("Expected 10", 10d, Calc.divide(100d, 10d), 0.001d);
	}
	
}
