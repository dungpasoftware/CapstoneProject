package fu.rms.service;

import java.util.List;

import fu.rms.dto.TableDto;

public interface ITableService {

	TableDto findByTableId(Long tableId);

	TableDto updateStatus(Long tableId, Long status);
	
	List<TableDto> getTableByLocation(Long locationId);
	
	int updateTableNewOrder();
	
	List<TableDto> getListTable();
	
	int updateChangeTable();
	
}
