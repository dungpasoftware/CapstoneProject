package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.OrderDto;
import fu.rms.newDto.OrderChef;
import fu.rms.newDto.OrderDetail;
import fu.rms.request.OrderChefRequest;
import fu.rms.service.IOrderService;

@RestController
@RequestMapping(value = "",produces = "application/json;charset=UTF-8")
public class OrderController {
	
	@Autowired
	IOrderService orderService;

	
	@GetMapping("/order/get-order-by-table/{id}")
	public OrderDto getCurrentOrderByTable(@PathVariable("id") Long tableId) {
		return orderService.getCurrentOrderByTable(tableId);
	}
	
	@PostMapping("/order/create-order")
	public OrderDto createOrder(@RequestBody OrderDto dto) {
		OrderDto result = orderService.insertOrder(dto);
		return result;
	}
	
	@PutMapping("/order/change-table")
	public String changeOrderTable(@RequestBody OrderDto dto, @RequestParam Long tableId) {
		return orderService.changeOrderTable(dto, tableId);
	}
	
	@GetMapping("/order/{id}")
	public OrderDetail getOrderById(@PathVariable("id") Long orderId) {
		return orderService.getOrderById(orderId);
	}
	
	@GetMapping("/order/all")
	public List<OrderDto> getListOrder() {
		return orderService.getListOrder();
	}
	
	@GetMapping("/order/by-order-taker/{id}")
	public List<OrderDto> getListOrderByOrderTaker(@PathVariable("id") Long staffId) {
		return orderService.getListByOrderTaker(staffId);
	}
	
	@PutMapping("/order/save-order")
	public OrderDetail saveOrder(@RequestBody OrderDto dto) {
		return orderService.updateSaveOrder(dto);
	}
	@PutMapping("/order/comment")
	public int updateComment(@RequestBody OrderDto dto) {
		return orderService.updateComment(dto);
	}

	@PutMapping("/order/chef-dish")
	public OrderChef updateOrderDishInOrder(@RequestBody OrderChefRequest request) {
		return orderService.updateOrderChef(request);
	}
	
	@PutMapping("/order/chef-order")
	public OrderChef updateOrder(@RequestBody OrderChefRequest request) {
		return orderService.updateOrderChef(request);
	}
	
	@PutMapping("/order/waiting-for-payment")
	public String updateWaitingPayOrder(@RequestBody OrderDto dto) {
		return orderService.updateStatusWaitingPayOrder(dto);
	}
	
	@PutMapping("/order/payment-order")
	public int updatePaymentOrder(@RequestBody OrderDto dto) {
		return orderService.updatePayOrder(dto, StatusConstant.STATUS_ORDER_DONE);
	}
	
	@PutMapping("/order/cancel")
	public int updateCancelOrder(@RequestBody OrderDto dto) {
		return orderService.updateCancelOrder(dto);
	}
	
	@GetMapping("/order/chef")
	public List<OrderChef> getListOrderChef() {
		return orderService.getListDisplayChefScreen();
	}

}
