package fu.rms.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import fu.rms.constant.Constant;

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
		if(diffSeconds <0) return null;
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

	public static boolean getTimeToNotification(Timestamp orderTime, Float timeToComplete) {
		Float time = null;
		if(timeToComplete == null || timeToComplete==0) return false;
		
		long diffSeconds = (getCurrentTime().getTime() - orderTime.getTime()) / 1000;
		long diffMinutes = diffSeconds >= 60 ? diffSeconds / 60 : diffSeconds % 60;
		time = (float) diffMinutes;
		
		if (time >= timeToComplete * 1.2)
			return true; // bếp chậm việc
		return false;
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
	 * tự sinh mã export
	 */
	public static String generateExportCode() {

		String newExportCode = "RMS-Export";
		newExportCode += new SimpleDateFormat("MMdd").format(new Date());
		newExportCode += "-" + randomAlphaNumberic(3);
		return newExportCode;
	}
	
	/*
	 * tự sinh mã import, exort code cho kiểm kê
	 */
	public static String generateInventoryImExCode(String inventoryCode) {

		String newExportCode = "RMS-Inv";
		newExportCode += new SimpleDateFormat("MMdd").format(new Date());
		newExportCode += "-" + inventoryCode;
		return newExportCode;
	}

	/*
	 * Thêm ngày vào TimeStamp
	 */

	public static Timestamp getTimeStampWhenAddDay(Integer day) {
		if (day == null)
			return null;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		cal.add(Calendar.DAY_OF_WEEK, day);
		timestamp.setTime(cal.getTime().getTime());
		return timestamp;
	}


	public static String generateDuplicateCode(String code) {

		StringBuilder sb = new StringBuilder(code);
		StringBuilder numberOfDuplicate = new StringBuilder();
		for (int i = sb.length() - 1; i >= 0; i--) {
			if (Character.isDigit(sb.charAt(i))) {
				numberOfDuplicate.append(sb.charAt(i));
				sb.deleteCharAt(i);
			} else {
				break;
			}
		}

		if (numberOfDuplicate.length() == 0) {
			numberOfDuplicate.append(0);
		} else {
			numberOfDuplicate.reverse();
		}
		sb.append(Integer.parseInt(numberOfDuplicate.toString()) + 1);
		return sb.toString();
	}

	public static Double roundUpDecimal(Double decimal) {
		if(decimal==null) return null;
		decimal = Math.ceil(decimal);
		return decimal;
	}
	
	public static Double sumBigDecimalToDouble(Double d1, Double d2) {			// cộng 2 số double
		if(d1 == null || d2 == null) return null;
		BigDecimal bd1 = BigDecimal.valueOf(d1);
		BigDecimal bd2 = BigDecimal.valueOf(d2);
		BigDecimal sum = bd1.add(bd2).setScale(3, BigDecimal.ROUND_HALF_EVEN);
		return sum.doubleValue();
	}
	
	public static Double subtractBigDecimalToDouble(Double d1, Double d2) {		// trừ 2 số double
		if(d1 == null || d2 == null) return null;
		BigDecimal bd1 = BigDecimal.valueOf(d1);
		BigDecimal bd2 = BigDecimal.valueOf(d2);
		BigDecimal minus = bd1.subtract(bd2).setScale(3, BigDecimal.ROUND_HALF_EVEN);
		return minus.doubleValue();
	}
	
	public static Double multiBigDecimalToDouble(Double d1, Double d2) {		// nhân 2 số double
		if(d1 == null || d2 == null) return null;
		BigDecimal bd1 = BigDecimal.valueOf(d1);
		BigDecimal bd2 = BigDecimal.valueOf(d2);
		BigDecimal multi = bd1.multiply(bd2).setScale(3, BigDecimal.ROUND_HALF_EVEN);
		return multi.doubleValue();
	}
	
	public static Double divideBigDecimalToDouble(Double d1, Double d2) {		// chia 2 số double
		if(d1 == null || d2 == null) return null;
		BigDecimal bd1 = BigDecimal.valueOf(d1);
		BigDecimal bd2 = BigDecimal.valueOf(d2);
		BigDecimal divide = bd1.divide(bd2,3,BigDecimal.ROUND_HALF_EVEN);
		return divide.doubleValue();
	}
	
	

}
