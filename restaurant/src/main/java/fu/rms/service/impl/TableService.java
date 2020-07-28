package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.Constant;
import fu.rms.constant.StatusConstant;
import fu.rms.dto.OrderDto;
import fu.rms.dto.TableDto;
import fu.rms.entity.Tables;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;
import fu.rms.mapper.TablesMapper;
import fu.rms.repository.TableRepository;
import fu.rms.service.ITableService;
import fu.rms.utils.Utils;

@Service
public class TableService implements ITableService {

	@Autowired
	private TableRepository tableRepo;
	@Autowired 
	private TablesMapper tableMapper;
	@Autowired
	OrderService orderService;
	
	@Override
	public TableDto findByTableId(Long tableId) {
		Tables table=tableRepo.findById(tableId)
				.orElseThrow(()-> new NotFoundException("Not Found Table") );
		TableDto tableDto=tableMapper.entityToDto(table);
		return tableDto;
		
	}
	
	/**
	 * update status khi order xong
	 */
	@Override
	public int updateStatusOrdered(Long tableId, Long status) {
		int result = 0;
		if(status==0) {
			throw new UpdateException("Failed Update Table Status"); 
		}
		result = tableRepo.updateStatusOrdered(tableId, status);
		return result;
	}
	
	/**
	 * lấy list table by location
	 */
	@Override
	public List<TableDto> getTableByLocation(Long locationId) {
		
		List<Tables> listEntity = tableRepo.findTablesByLocation(locationId);
		List<TableDto> listDto = listEntity.stream().map(tableMapper::entityToDtoByLocation).collect(Collectors.toList());
		
		return listDto;
	}
	
	/**
	 * update khi có 1 order mới tạo
	 */
	@Override
	public int updateTableNewOrder(OrderDto orderDto, Long statusId) {
		
		int result = 0;
		if(orderDto != null) {
			result = tableRepo.updateTableNewOrder(orderDto.getOrderId(), orderDto.getOrderTakerStaffId(), orderDto.getTableId(), statusId);
		}

		return result;
	}

	/**
	 * lấy danh sách tất cả các bàn
	 */
	@Override
	public List<TableDto> getListTable() {
		List<Tables> listEntity = tableRepo.findAll();
		List<TableDto> listDto = listEntity.stream().map(tableMapper::entityToDtoByLocation).collect(Collectors.toList());
		return listDto;
	}

	/**
	 * thay đổi bàn của order, trở về trạng thái ready
	 */
	@Override
	public int updateToReady(Long tableId, Long statusId) {
		
		int result = 0;
		try {
			result = tableRepo.updateToReady(tableId, statusId);
		} catch (Exception e) {
			return Constant.RETURN_ERROR_NULL;
		}
		return result;
	}

}
