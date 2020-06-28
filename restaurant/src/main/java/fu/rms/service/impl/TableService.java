package fu.rms.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.Constant;
import fu.rms.dto.TableDto;
import fu.rms.entity.Tables;
import fu.rms.mapper.TableMapper;
import fu.rms.repository.StatusRepository;
import fu.rms.repository.TableRepository;
import fu.rms.service.ITableService;

@Service
public class TableService implements ITableService {

	@Autowired
	private TableRepository tableRepo;
	@Autowired 
	private StatusRepository statusRepo;
	@Autowired 
	private TableMapper tableMapper;
	@Override
	public TableDto findByTableId(Long tableId) {
		Optional<Tables> tables=tableRepo.findById(tableId);
		TableDto tableDto=tableMapper.entityToDto(tables.get());
		String statusValue=statusRepo.findByStatusNameAndStatusCode(Constant.TABLE_STATUS, 
				tables.get().getStatus()).getStatusValue();
		tableDto.setStatusValue(statusValue);
		return tableDto;
		
	}
	@Override
	public TableDto updateStatus(Long tableId, int status) {
		tableRepo.setStatus(tableId, status);	
		TableDto tableDto=findByTableId(tableId);
		return tableDto;
	}

}
