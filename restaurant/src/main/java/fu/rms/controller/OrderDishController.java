package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.OrderDishDto;
import fu.rms.service.IOrderDishService;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class OrderDishController {
	
	@Autowired
	IOrderDishService orderdishService;

	@GetMapping("/order-dish/by-order/{orderId}")
	public List<OrderDishDto> listOrderDish(@PathVariable("orderId") Long orderId) {
		return orderdishService.getListOrderDishByOrder(orderId);
	}
	
	@GetMapping("/order-dish/{id}")
	public OrderDishDto getOrderDish(@PathVariable("id") Long orderDishId) {
		return orderdishService.getOrderDishById(orderDishId);
	}
	
	@PutMapping("/order-dish/update-quantity")
	public int updateQuantityOrderDish(@RequestBody OrderDishDto dto) {
		return orderdishService.updateQuantityOrderDish(dto);
	}
	
	@PutMapping("/order-dish/update-topping")
	public int updateToppingOrderDish(@RequestBody OrderDishDto dto) {
		return orderdishService.updateToppingCommentOrderDish(dto);
	}
//	
//	@PutMapping("/order-dish/update-comment")
//	public int updateCommentOrderDish(@RequestBody OrderDishDto dto) {
//		return orderdishService.updateQuantityOrderDish(dto);
//	}
	
	@PutMapping("/order-dish/cancel")
	public int updateCancelOrderDish(@RequestBody OrderDishDto dto) {
		return orderdishService.updateCancelOrderDish(dto);
	}
	
	@PutMapping("/order-dish/chef-preparation")
	public int updatePreparationOrderDish(@RequestBody OrderDishDto dto) {
		return orderdishService.updateStatusOrderDish(dto, StatusConstant.STATUS_ORDER_DISH_PREPARATION);
	}
	
	@PutMapping("/order-dish/chef-cooked")
	public int updateCookedOrderDish(@RequestBody OrderDishDto dto) {
		return orderdishService.updateStatusOrderDish(dto, StatusConstant.STATUS_ORDER_DISH_JUST_COOKED);
	}
	
	@PutMapping("/order-dish/ot-completed")
	public int updateCompletedOrderDish(@RequestBody OrderDishDto dto) {
		return orderdishService.updateStatusOrderDish(dto, StatusConstant.STATUS_ORDER_DISH_COMPLETED);
	}
	
}
