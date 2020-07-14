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
import fu.rms.newDto.OrderDetail;
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
	
	@PutMapping("/order/change-order-table")
	public int changeOrderTable(@RequestBody OrderDto dto, @RequestParam("tableId") Long tableId) {
		return orderService.updateOrderCashier(dto, tableId);
	}
	
	@GetMapping("/order/{id}")
	public OrderDetail getOrderById(@PathVariable("id") Long orderId) {
		return orderService.getOrderById(orderId);
	}
	
	@GetMapping("/order/all")
	public List<OrderDto> getListOrder() {
		return orderService.getListOrder();
	}
	
	@PutMapping("/order/save-order")
	public int saveOrder(@RequestBody OrderDto dto) {
		return orderService.updateSaveOrder(dto);
	}
	
	@GetMapping("/order/by-order-taker/{id}")
	public List<OrderDto> getListOrderByOrderTaker(@PathVariable("id") Long staffId) {
		return orderService.getListByOrderTaker(staffId);
	}

	@PutMapping("/order/chef-confirmed")
	public int updateConfirmedOrder(@RequestBody OrderDto dto) {
		return orderService.updateOrderChef(dto, StatusConstant.STATUS_ORDER_CONFIRMED);
	}
	
	@PutMapping("/order/chef-cooked")
	public int updateCookedOrder(@RequestBody OrderDto dto) {
		return orderService.updateStatusOrder(dto, StatusConstant.STATUS_ORDER_JUST_COOKED);
	}
	
	@PutMapping("/order/ot-completed")
	public int updateCompletedOrder(@RequestBody OrderDto dto) {
		return orderService.updateStatusOrder(dto, StatusConstant.STATUS_ORDER_COMPLETED);
	}
	
	@PutMapping("/order/cancel")
	public int updateCancelOrder(@RequestBody OrderDto dto) {
		return orderService.updateCancelOrder(dto, StatusConstant.STATUS_ORDER_CANCELED);
	}
	
	@PutMapping("/order/waitting-pay-order")
	public int updateWaittingPayOrder(@RequestBody OrderDto dto) {
		return orderService.updateOrderCashier(dto, StatusConstant.STATUS_ORDER_WAITTING_FOR_PAY);
	}
	
	@PutMapping("/order/payment-order")
	public int updatePaymentOrder(@RequestBody OrderDto dto) {
		return orderService.updateCancelOrder(dto, StatusConstant.STATUS_ORDER_DONE);
	}
	
	
}
