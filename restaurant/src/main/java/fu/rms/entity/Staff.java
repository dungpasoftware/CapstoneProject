package fu.rms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "staffs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {

	
	@Id
	@Column(name="staff_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long staffId;
	
	@Column(name="staff_code")
	private String staffCode;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="full_name")
	private String fullname;
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;
	
	@Column(name="staff_phone")
	private String staff_phone;
	
	@Column(name="address")
	private String address;
	
	@Column(name="is_online")
	private int isOnline;
	
	@Column(name="is_activated")
	private int isActivated;
	
	@Column(name="create_date")
	private Date createDate;
	
	@Column(name="last_login")
	private Date lastLogin;
	
	@Column(name="last_order")
	private Date lastOrder;
	
	@Column(name="create_by")
	private String createBy;
	
	
	@OneToMany(mappedBy = "orderTakerStaff")
	List<Order> orderTakerOrder;
	
	@OneToMany(mappedBy = "chefStaff")
	List<Order> chefOrder;
	
	@OneToMany(mappedBy = "cashierStaff")
	List<Order> cashierOrder;
	
	
	
	
	
	
}
