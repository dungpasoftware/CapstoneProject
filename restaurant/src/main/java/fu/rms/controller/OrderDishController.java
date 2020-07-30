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
import fu.rms.request.OrderDishChefRequest;
import fu.rms.request.OrderDishRequest;
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
	public String updateQuantityOrderDish(@RequestBody OrderDishDto dto) {
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
	public String updateCancelOrderDish(@RequestBody OrderDishDto dto) {
		return orderdishService.updateCancelOrderDish(dto);
	}
	
	@PutMapping("/order-dish/chef-preparation")
	public int updatePreparationOrderDish(@RequestBody OrderDishChefRequest request) {
		return orderdishService.updateStatusOrderDish(request, StatusConstant.STATUS_ORDER_DISH_PREPARATION);
	}
	
	@PutMapping("/order-dish/chef-completed")
	public int updateCompletedOrderDish(@RequestBody OrderDishChefRequest request) {
		return orderdishService.updateStatusOrderDish(request, StatusConstant.STATUS_ORDER_DISH_COMPLETED);
	}
	
	@GetMapping("/order-dish/return/{orderId}")
	public List<OrderDishDto> getCanReturnByOrderId (@PathVariable("orderId") Long orderId) {
		return orderdishService.getCanReturnByOrderId(orderId);
	}
	
	@PutMapping("/order-dish/return")
	public String updateReturnOrderDish(@RequestBody List<OrderDishRequest> listOdr) {
		return orderdishService.updateReturnDish(listOdr);
	}
}
