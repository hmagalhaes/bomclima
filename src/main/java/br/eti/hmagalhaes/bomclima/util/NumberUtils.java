package br.eti.hmagalhaes.bomclima.util;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

public class NumberUtils {

	public static float parseFloat(final String value, final String numberPattern) {
		if (StringUtils.isBlank(value)) {
			throw new IllegalArgumentException("Valor não pode ser vazio");
		}
		try {
			final DecimalFormat f = new DecimalFormat(numberPattern);
			final Number n = f.parse(value);

			return n.floatValue();
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
