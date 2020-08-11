package fu.rms.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.TableDto;
import fu.rms.entity.Tables;
import fu.rms.newDto.OrderDtoTable;
import fu.rms.newDto.StaffDtoTable;
import fu.rms.utils.Utils;

@Component
public class TablesMapper {

	private static final Logger logger = LoggerFactory.getLogger(TablesMapper.class);
	
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
		try {
			dto.setTableId(entity.getTableId());
			dto.setTableCode(entity.getTableCode());
			dto.setTableName(entity.getTableName());
			dto.setLocationId(entity.getLocationTable().getLocationTableId());
			dto.setStatusId(entity.getStatus().getStatusId());
			dto.setStatusValue(entity.getStatus().getStatusValue());
			dto.setMaxCapacity(entity.getMaxCapacity());
			dto.setMinCapacity(entity.getMinCapacity());
			if(dto.getStatusId().equals(StatusConstant.STATUS_TABLE_ORDERED)) {
				OrderDtoTable orderTalble = new OrderDtoTable(entity.getOrder().getOrderId(), entity.getOrder().getOrderCode(), 
						entity.getOrder().getStatus().getStatusId(), 
						entity.getOrder().getStatus().getStatusValue(), entity.getOrder().getComment(),
						entity.getOrder().getTotalAmount(), entity.getOrder().getTotalItem(), entity.getOrder().getOrderDate(),
						Utils.getOrderTime(Utils.getCurrentTime(), entity.getOrder().getOrderDate()));
				dto.setOrderDto(orderTalble);
				StaffDtoTable staffTable = new StaffDtoTable(entity.getStaff().getStaffId(), entity.getStaff().getStaffCode());
				dto.setStaffDto(staffTable);
			} else if(dto.getStatusId().equals(StatusConstant.STATUS_TABLE_BUSY)) {
				OrderDtoTable orderTable = new OrderDtoTable(entity.getOrder().getOrderId(), entity.getOrder().getOrderCode(), 
						entity.getOrder().getStatus().getStatusId(), 
						entity.getOrder().getStatus().getStatusValue(), entity.getOrder().getComment(), null, null, null, null);
				dto.setOrderDto(orderTable);
				StaffDtoTable staffTable = new StaffDtoTable(entity.getStaff().getStaffId(), entity.getStaff().getStaffCode());
				dto.setStaffDto(staffTable);
			}
		} catch (NullPointerException e) {
			logger.info(e.getLocalizedMessage());
		}
		
		return dto;
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
