package fu.rms.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fu.rms.mapper.TablesMapper;

public class Constant {

	// Status
	public static final String LOCATION_TABLE_STATUS = "LOCATION_TABLE_STATUS";
	public static final String TABLE_STATUS = "TABLE_STATUS";
	public static final String DISH_STATUS = "DISH_STATUS";
	public static final String ORDER_STATUS = "ORDER_STATUS";
	public static final String ORDER_DISH_STATUS = "ORDER_DISH_STATUS";
	public static final String OPTION_STATUS = "OPTION_STATUS";

	public static final String ALPHA_NUMBERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	public static final int RETURN_ERROR_NULL = 0;
	
	public static Logger logger = null;
	
	public static final String TABLE_BUSY = "Bàn này đang bận";
	public static final String TABLE_ORDERED = "Bàn này đang có khách ngồi";
	public static final String TABLE_READY = "Có thể đổi";
	public static final String TABLE_ERROR = "Đổi bàn không thành công";
	
	public static final Double MATERIAL_EXPORT_ZERO = (double) 0;
}
