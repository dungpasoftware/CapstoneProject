package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.TableDto;
import fu.rms.entity.Tables;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;
import fu.rms.mapper.TableMapper;
import fu.rms.repository.OrderRepository;
import fu.rms.repository.TableRepository;
import fu.rms.service.ITableService;

@Service
public class TableService implements ITableService {

	@Autowired
	private TableRepository tableRepo;
	@Autowired 
	private TableMapper tableMapper;
	
	@Autowired
	OrderRepository orderRepo;
	
	@Override
	public TableDto findByTableId(Long tableId) {
		Tables table=tableRepo.findById(tableId)
				.orElseThrow(()-> new NotFoundException("Not Found Table") );
		TableDto tableDto=tableMapper.entityToDto(table);
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
		
		List<Tables> listTable = tableRepo.findTablesByLocation(locationId);
		List<TableDto> dtos = listTable.stream().map(tableMapper::entityToDto).collect(Collectors.toList());
		
		return dtos;
	}
	
	
	@Override
	public TableDto updateTableNewOrder(Long tableId, Long staffId) {
		Long status = (long) 4;
		Long orderId = (Long) orderRepo.getLastestOrderId();
		
		tableRepo.updateTableNewOrder(orderId, staffId, tableId, status);
		TableDto dto = findByTableId(tableId);
		
		return dto;
	}

}
