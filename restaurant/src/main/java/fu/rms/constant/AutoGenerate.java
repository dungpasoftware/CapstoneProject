package fu.rms.constant;

public class AutoGenerate {
	
	

	public static String generateOrderCode(String lastestOrderCode) {
		
		String newOrderCode = "RMS";
		
		if(lastestOrderCode != null && !lastestOrderCode.isEmpty()) {
			
			
		}			
		
		
		return newOrderCode;
	}
	
	public static String generateStaffCode(String fullNameStaff) {
		
		String staffCode = "";
	
		if(fullNameStaff != null && !fullNameStaff.isEmpty()) {
			String [] words = fullNameStaff.split(" ");
			staffCode = words[words.length-1].toUpperCase();
			for (int i = 0; i < words.length-1; i++) {
				if(words[i].trim() != "") {
					staffCode += words[i].substring(0, 1).toUpperCase();
				}
			}
		}		
		return staffCode;
	}
}
