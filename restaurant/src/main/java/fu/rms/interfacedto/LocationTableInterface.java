package fu.rms.interfacedto;

import org.springframework.beans.factory.annotation.Value;

import fu.rms.dto.TableDto;

public interface LocationTableInterface {

	Integer getLocationId();
	
	String getLocationName();
	
	String getLocationStatus();
	
	@Value("#{@tablesMapper.toDto(target.tableCode, target.tableName, target.maxCapacity, target.tableStatus)}")
	TableDto getTable();
		
	
	
//	String getTableName();
//	String getTableCode();
//	String getTableStatus();
//	Integer getMaxCapacity();
	
}
