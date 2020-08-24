package fu.rms.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {

	public static LocalDateTime convertStringToLocalDateTime(String date) {
		if (StringUtils.isBlank(date))
			return null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("Asia/Ho_Chi_Minh"));
		LocalDateTime localDateTime = LocalDate.parse(date, formatter).atStartOfDay();
		return localDateTime;
	}

	public static String convertLocalDateTimeToString(LocalDateTime localDateTime) {
		if (localDateTime == null)
			return null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").withZone(ZoneId.of("Asia/Ho_Chi_Minh"));
		String date = localDateTime.format(formatter);
		return date;
	}
	
	public static String convertTimeToString(LocalDateTime localDateTime) {
		if (localDateTime == null)
			return null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("Asia/Ho_Chi_Minh"));
		String date = localDateTime.format(formatter);
		return date;
	}

	public static LocalDateTime localDateTimeAddDay(Integer days) {
		if (days == null)
			return null;
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.plusDays(days);

	}

}
