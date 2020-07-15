package fu.rms.newDto;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Value;

public interface GetByDish {

	Long getDishId();
	
	String getDishName();
	
	Integer getTotalQuantity();
	
	Integer getQuantityOrderDish();
	
	Long getOrderDishId();
	
	String getStatusOrderDish();
	
	Long getOrderId();
	
	String getTableName();
	
	String getStatusOrder();
	
	Timestamp getOrderDate();
	
	String getOrderCode();
	
	@Value("#{@optionByDishMapper.buildMapper(target.optionName, target.quantityOrderDishOption)}")
	OptionByDish getOptionByDish();
}
