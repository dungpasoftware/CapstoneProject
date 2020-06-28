package fu.rms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long orderId;
	
	@ManyToOne
	@JoinColumn(name="order_taker_id")
	private Staff orderTakerStaff;
	
	@ManyToOne
	@JoinColumn(name="chef_id")
	private Staff chefStaff;
	
	@ManyToOne
	@JoinColumn(name="cashier_id")
	private Staff cashierStaff;
	
	@ManyToOne()
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToOne()
	@JoinColumn(name="table_id")
	private Tables table;
	
	@Column(name = "order_code")
	private String orderCode;
	
	@Column(name = "status")
	private Long status;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="total_amount")
	private double totalAmount;
	
	@Column(name="total_item")
	private int totalItem;
	
	@Column(name="order_date")
	private Date orderDate;
	
	@Column(name="payment_date")
	private Date paymentDate;
	
	@Column(name="modified_date")
	private Date modifiedDate;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@Column(name="time_to_complete")
	private float timeToComplete;
	
//	@OneToMany(mappedBy = "orderId")
//	List<OrderDish> orderDish;
	
	
}
