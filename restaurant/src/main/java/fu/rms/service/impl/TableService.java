package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.constant.Utils;
import fu.rms.dto.OrderDto;
import fu.rms.dto.TableDto;
import fu.rms.entity.Tables;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;
import fu.rms.mapper.OrderMapper;
import fu.rms.mapper.StaffMapper;
import fu.rms.mapper.StatusMapper;
import fu.rms.mapper.TablesMapper;
import fu.rms.repository.TableRepository;
import fu.rms.service.ITableService;

@Service
public class TableService implements ITableService {

	@Autowired
	private TableRepository tableRepo;
	@Autowired 
	private TablesMapper tableMapper;
	@Autowired
	OrderService orderService;
	@Autowired 
	private OrderMapper orderMapper;
	@Autowired 
	private StaffMapper staffMapper;
	
	@Override
	public TableDto findByTableId(Long tableId) {
		Tables table=tableRepo.findById(tableId)
				.orElseThrow(()-> new NotFoundException("Not Found Table") );
		TableDto tableDto=tableMapper.entityToDto(table);
//		tableDto.setOrderDto(orderMapper.entityToDto(table.getOrder()));
//		tableDto.setStaffDto(staffMapper.entityToDto(table.getStaff()));
		return tableDto;
		
	}
	
	@Override
	public TableDto updateStatus(Long tableId, Long status) {
		tableRepo.setStatus(tableId, status);
		if(status==0) {
			throw new UpdateException("Failed Update Table Status"); 
		}
		TableDto tableDto=findByTableId(tableId);
		return tableDto;
	}
	
	@Override
	public List<TableDto> findListTableByLocation(Long locationId) {
		
		List<Tables> listEntity = tableRepo.findTablesByLocation(locationId);
		List<TableDto> listDto = null;
		if(listEntity != null && listEntity.size() != 0) {
			listDto = listEntity.stream().map(tableMapper::entityToDto).collect(Collectors.toList());
				for (int i = 0; i < listDto.size(); i++) {
					listDto.get(i).getOrder().setOrderTime(Utils.getOrderTime(Utils.getCurrentTime(), listDto.get(i).getOrder().getOrderDate()));
			}
		}
		
		return listDto;
	}
	
	
	@Override
	public int updateTableNewOrder() {
		OrderDto orderDto = orderService.getLastestOrder();
		
		int result = tableRepo.updateTableNewOrder(orderDto.getOrderId(), orderDto.getOrderTakerStaffId(), orderDto.getTableId(), StatusConstant.STATUS_TABLE_BUSY);
		
		return result;
	}

	@Override
	public List<TableDto> findListTable() {
		List<Tables> listEntity = tableRepo.findAll();
		List<TableDto> listDto = listEntity.stream().map(tableMapper::entityToDto).collect(Collectors.toList());
		return listDto;
	}

	@Override
	public int updateChangeTable() {
		
		int result = 0;
		
		
		return 0;
	}

}
