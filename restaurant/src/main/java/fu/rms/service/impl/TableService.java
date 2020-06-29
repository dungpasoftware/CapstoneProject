package fu.rms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.TableDto;
import fu.rms.entity.Tables;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;
import fu.rms.mapper.TableMapper;
import fu.rms.repository.TableRepository;
import fu.rms.service.ITableService;

@Service
public class TableService implements ITableService {

	@Autowired
	private TableRepository tableRepo;
	@Autowired 
	private TableMapper tableMapper;
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

}
