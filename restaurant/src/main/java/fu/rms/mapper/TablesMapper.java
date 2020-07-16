package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.constant.Utils;
import fu.rms.dto.TableDto;
import fu.rms.entity.Tables;
import fu.rms.newDto.OrderDtoNew;
import fu.rms.newDto.StaffDtoNew;

@Component
public class TablesMapper {

	@Autowired
	ModelMapper modelMapper;
	
	public TableDto entityToDto(Tables entity) {

		TableDto dto = new TableDto();
		dto.setTableId(entity.getTableId());
		dto.setTableCode(entity.getTableCode());
		dto.setTableName(entity.getTableName());
		dto.setStatusValue(entity.getStatus().getStatusValue());
		dto.setMaxCapacity(entity.getMaxCapacity());
		dto.setMinCapacity(entity.getMinCapacity());
		return dto;
		
	}
	
	public TableDto entityToDtoByLocation(Tables entity) {
		TableDto dto = new TableDto();
		dto.setTableId(entity.getTableId());
		dto.setTableCode(entity.getTableCode());
		dto.setTableName(entity.getTableName());
		dto.setLocationId(entity.getLocationTable().getLocationTableId());
		dto.setStatusValue(entity.getStatus().getStatusValue());
		dto.setMaxCapacity(entity.getMaxCapacity());
		dto.setMinCapacity(entity.getMinCapacity());
		if(dto.getStatusValue().equals("ORDERED")) {
			OrderDtoNew orderNew = new OrderDtoNew(entity.getOrder().getOrderId(), entity.getOrder().getOrderCode(), 
					entity.getOrder().getStatus().getStatusId(), 
					entity.getOrder().getStatus().getStatusValue(), entity.getOrder().getComment(),
					entity.getOrder().getTotalAmount(), entity.getOrder().getTotalItem(), entity.getOrder().getOrderDate(),
					Utils.getOrderTime(Utils.getCurrentTime(), entity.getOrder().getOrderDate()));
			dto.setOrderDto(orderNew);
			StaffDtoNew staffNew = new StaffDtoNew(entity.getStaff().getStaffId(), entity.getStaff().getStaffCode());
			dto.setStaffDto(staffNew);
		} else if(dto.getStatusValue().equals("BUSY")) {
			OrderDtoNew orderNew = new OrderDtoNew(entity.getOrder().getOrderId(), entity.getOrder().getOrderCode(), 
					entity.getOrder().getStatus().getStatusId(), 
					entity.getOrder().getStatus().getStatusValue(), null, null, null, null, null);
			dto.setOrderDto(orderNew);
			StaffDtoNew staffNew = new StaffDtoNew(entity.getStaff().getStaffId(), entity.getStaff().getStaffCode());
			dto.setStaffDto(staffNew);
		}
		return dto;
	}

	public Tables dtoToEntity(TableDto tableDto) {
		Tables table = modelMapper.map(tableDto, Tables.class);
		return table;
	}
	
	public TableDto toDto(String tableCode, String tableName, Integer maxCapacity, String statusValue) {
		TableDto dto = new TableDto();
		dto.setMaxCapacity(maxCapacity);
		dto.setTableCode(tableCode);
		dto.setTableName(tableName);
		dto.setStatusValue(statusValue);
		return dto;
	}
	
}
