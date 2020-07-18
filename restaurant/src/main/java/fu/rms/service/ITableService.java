package fu.rms.service;

import java.util.List;

import fu.rms.dto.OrderDto;
import fu.rms.dto.TableDto;

public interface ITableService {

	TableDto findByTableId(Long tableId);

	int updateStatusOrdered(Long tableId, Long status);
	
	List<TableDto> getTableByLocation(Long locationId);
	
	int updateTableOrder(OrderDto orderDto, Long statusId);
	
	List<TableDto> getListTable();
	
	int updateChangeTable(Long tableId, Long statusId);
	
}
