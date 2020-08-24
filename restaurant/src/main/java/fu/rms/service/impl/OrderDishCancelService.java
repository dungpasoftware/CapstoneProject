package fu.rms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.constant.Constant;
import fu.rms.dto.OrderDishCancelDto;
import fu.rms.exception.NullPointerException;
import fu.rms.repository.OrderDishCancelRepository;
import fu.rms.service.IOrderDishCancel;

@Service
public class OrderDishCancelService implements IOrderDishCancel{
	
	@Autowired
	OrderDishCancelRepository orderDishCancelRepo;

	@Override
	@Transactional
	public String insertCancel(OrderDishCancelDto dto) {

		try {
			orderDishCancelRepo.insert(dto.getQuantityCancel(), dto.getCommentCancel(), dto.getCancelBy(), dto.getCancelDate(), dto.getOrderDishId());
		} catch(Exception e) {
			throw new NullPointerException("Có gì đó xảy ra");
		}
		
		return Constant.INSERT_SUCCESS;
	}

	
}
