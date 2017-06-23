package br.eti.hmagalhaes.bomclima.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class NumberUtilsTest {

	@Test
	public void testOfParseFloat() {
		assertEquals(10.11, NumberUtils.parseFloat("10,11", "0,0"), 0.009f);
		assertEquals(100.0, NumberUtils.parseFloat("100,0", "0,0"), 0.009f);
		assertEquals(1.80, NumberUtils.parseFloat("1,80", "0,0"), 0.009f);
		assertEquals(2.0, NumberUtils.parseFloat("2", "0,0"), 0.009f);
	}
}
