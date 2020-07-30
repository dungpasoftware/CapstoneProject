package fu.rms.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"importCode","createdDate","supplierName","warehouseName","materialName","quantity","unitPrice","totalAmount","expireDate"})
public interface ImportMaterialDetailDto {

	String getImportCode();
	
	String getCreatedDate();
	
	String getSupplierName();
	
	String getWarehouseName();
	
	String getMaterialName();
	
	Double getQuantity();
	
	Double getUnitPrice();
	
	Double getTotalAmount();
	
	String getExpireDate();
}
