package br.eti.hmagalhaes.bomclima.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date parseDate(final String value, final String datePattern) {
		try {
			return new SimpleDateFormat(datePattern).parse(value);
		} catch (ParseException ex) {
			throw new IllegalArgumentException("Bad date => " + value, ex);
		}
	}

	public static boolean isBadDate(final String date, final String datePattern) {
		try {
			parseDate(date, datePattern);
			return false;
		} catch (IllegalArgumentException ex) {
			return true;
		}
	}

	public static Date tomorrowFirstSecond() {
		final Calendar tomorrow = Calendar.getInstance();
		tomorrow.clear(Calendar.MILLISECOND);
		tomorrow.clear(Calendar.SECOND);
		tomorrow.clear(Calendar.MINUTE);
		tomorrow.clear(Calendar.HOUR_OF_DAY);
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		return tomorrow.getTime();
	}
}
