package fu.rms.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import fu.rms.entity.Staff;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

	private Long orderId;
	
	private String orderCode;
	
	private Long statusId;
	
	private String statusValue;
	
//	private String comment;
	
	private Double totalAmount;
	
	private Integer totalItem;
	
	private Timestamp orderDate;
	
	private String orderTime; //time order
	
//	private Date paymentDate;
//	
	private Date modifiedDate;
	
	private String modifiedBy;
//	
//	private String createBy;
//	
	private Long tableId;
	
	private String tableName;
	
//	private float timeToComplete;
	
	private Long orderTakerStaffId;
	private String orderTakerStaffCode;
	private String orderTakerStaffFullName;
	
	private Long chefStaffId;
	private String chefStaffCode;
	private String chefStaffFullName;
	
	private Long cashierStaffId;
	private String cashierStaffCode;
	private String cashierStaffFullName;
	
//	private Staff orderTakerStaff;
	
//	private Staff chefStaff;
//	
//	private Staff cashierStaff;
	
	
}
