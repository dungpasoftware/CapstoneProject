package fu.rms.service;

import fu.rms.dto.TableDto;

public interface ITableService {

	TableDto findByTableId(Long tableId);

	TableDto updateStatus(Long tableId, int status);
	
}
