package fu.rms.constant;

public class StatusConstant {

	/*
	 * Status Location table
	 */
	public static final Long STATUS_LOCATION_TABLE_READY = (long) 1;
	public static final Long STATUS_LOCATION_TABLE_EXPIRE = (long) 2;
	public static final Long STATUS_LOCATION_TABLE_FULL = (long) 3;
	
	/*
	 * Status table
	 */
	public static final Long STATUS_TABLE_READY = (long) 4;
	public static final Long STATUS_TABLE_BUSY = (long) 5;
	public static final Long STATUS_TABLE_ORDERED = (long) 6;
	
	
	/**
	 * Order status
	 */
	public static final Long STATUS_ORDER_ORDERING = (long) 10;
	public static final Long STATUS_ORDER_ORDERED = (long) 11;
	public static final Long STATUS_ORDER_CONFIRMED = (long) 11;
	public static final Long STATUS_ORDER_JUST_COOKED = (long) 12;
	public static final Long STATUS_ORDER_COMPLETED = (long) 13;
	public static final Long STATUS_ORDER_WAITTING_FOR_PAY = (long) 14;
	public static final Long STATUS_ORDER_DONE = (long) 15;
	public static final Long STATUS_ORDER_CANCELED = (long) 16;
	
}
