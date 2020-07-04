package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.OrderDto;
import fu.rms.entity.Order;
import fu.rms.service.impl.OrderService;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
	
	@Autowired
	OrderService orderService;

	
	@GetMapping("/order/get-order-by-table/{id}")
	public OrderDto getCurrentOrderByTable(@PathVariable("id") Long tableId) {
		return orderService.getCurrentOrderByTable(tableId);
	}
	
	@PostMapping("/order/create-order")
	public int createOrder(@RequestBody OrderDto dto) {
		return orderService.insertOrder(dto);
	}
	
	@PutMapping("/order/change-order-table")
	public int changeOrderTable(@RequestBody OrderDto dto, @RequestParam("tableId") Long tableId) {
		return orderService.updateOrderCashier(dto, tableId);
	}
	
	@GetMapping("/order/{id}")
	public OrderDto getOrderById(@PathVariable("id") Long orderId) {
		return orderService.getOrderById(orderId);
	}
	
	@GetMapping("/order")
	public List<OrderDto> getOrder() {
		return orderService.getOrder();
	}
}
