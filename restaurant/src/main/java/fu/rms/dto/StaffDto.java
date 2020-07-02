package fu.rms.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffDto {

	private int staffId;
	
	private String staffCode;
	
	private String email;
	
	private String password;
	
	private String fullname;
	
	private Long roleId;
	
	private String roleName;
	
	private Integer isOnline;
	
	private Integer isActivated;
	
	private Date createDate;
	
	private String createBy;
	
	private Date lastOrder;
	
	private Date lastLogin;
	
}
