package fu.rms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.OrderDto;
import fu.rms.entity.Order;
import fu.rms.service.impl.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;

	
	@GetMapping("/order/get_order_by_table/{table_id}")
	public OrderDto getCurrentOrderByTable(@PathVariable("table_id") Long tableId) {
		return orderService.getCurrentOrderByTable(tableId);
	}
	
	@PostMapping("/order/create")
	public int createOrder(@RequestBody OrderDto dto) {
		return orderService.insertOrder(dto);
	}
	
}
