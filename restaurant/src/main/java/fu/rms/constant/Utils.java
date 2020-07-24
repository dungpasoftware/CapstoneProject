package fu.rms.constant;

import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class Utils {

	/*
	 * tự sinh mã order
	 */
	public static String generateOrderCode() {

		String newOrderCode = "RMS";
		newOrderCode += new SimpleDateFormat("yyMMdd").format(new Date());
		newOrderCode += "-" + randomAlphaNumberic(6);
		return newOrderCode;
	}

	/*
	 * tự sinh mã staff
	 */
	public static String generateStaffCode(String fullNameStaff) {

		String staffCode = "";

		if (fullNameStaff != null && !fullNameStaff.isEmpty()) {
			String[] words = fullNameStaff.split(" ");
			staffCode = words[words.length - 1].toUpperCase();
			for (int i = 0; i < words.length - 1; i++) {
				if (words[i].trim() != "") {
					staffCode += words[i].substring(0, 1).toUpperCase();
				}
			}
		}
		return staffCode;
	}

	/*
	 * tự sinh mã staff
	 */
	private static String randomAlphaNumberic(int number) {
		Random rd = new Random();

		StringBuilder sb = new StringBuilder(number);
		for (int i = 0; i < number; i++) {
			sb.append(Constant.ALPHA_NUMBERIC.charAt(rd.nextInt(Constant.ALPHA_NUMBERIC.length())));
		}

		return sb.toString();
	}

	/*
	 * tính thời gian order theo ngày/giờ/phút. VD: 2 giờ 24p
	 */
	public static String getOrderTime(Timestamp currentTime, Timestamp orderTime) {
		String timeOrder = "";

		long diffSeconds = (currentTime.getTime() - orderTime.getTime()) / 1000;

		if (diffSeconds >= 86400) {
			long diffDays = diffSeconds / (24 * 60 * 60);
			timeOrder += String.valueOf(diffDays) + " ngày ";
			diffSeconds = diffSeconds - (24 * 60 * 60 * diffDays);
		}

		if (diffSeconds >= 3600) {
			long diffHours = diffSeconds / (60 * 60);
			timeOrder += String.valueOf(diffHours) + " giờ ";
			diffSeconds = diffSeconds - (60 * 60 * diffHours);
		}

		if (diffSeconds >= 60 && !timeOrder.contains("ngày")) {
			long diffMinutes = diffSeconds / (60);
			timeOrder += String.valueOf(diffMinutes) + " phút";
			diffSeconds = diffSeconds - (60 * 60 * diffMinutes);
		}
		if (timeOrder.equals("")) {
			timeOrder += String.valueOf(diffSeconds) + " giây";
		}

		return timeOrder.trim();
	}

	/*
	 * lấy thời gian hiện tại
	 */
	public static Timestamp getCurrentTime() {
		Date date = new Date();
		return new Timestamp(date.getTime());
	}

	/*
	 * tự sinh mã import
	 */
	public static String generateImportCode() {

		String newImportCode = "RMS-Import";
		newImportCode += new SimpleDateFormat("MMdd").format(new Date());
		newImportCode += "-" + randomAlphaNumberic(3);
		return newImportCode;
	}

	/*
	 * Thêm ngày vào TimeStamp
	 */

	public static Timestamp getTimeStampWhenAddDay(Integer day) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		cal.add(Calendar.DAY_OF_WEEK, day);
		timestamp.setTime(cal.getTime().getTime());
		return timestamp;
	}

	public static String timeStampToString(Timestamp timestamp) {
		Date date = new Date();
		date.setTime(timestamp.getTime());
		String formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
		return formattedDate;
	}

	public static String convertNameToCode(String name) {
		name = name.trim().toUpperCase().replaceAll("\\s+", "-");
		String temp = Normalizer.normalize(name, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}
	
	public static String generateDuplicateCode(String code, long numberOfDuplicate) {
		StringBuilder sb=new StringBuilder(code);
		sb.append(numberOfDuplicate+1);
		
		return sb.toString();
	}

}
