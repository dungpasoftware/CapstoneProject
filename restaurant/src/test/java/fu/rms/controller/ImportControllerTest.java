package fu.rms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import fu.rms.dto.ImportDto;
import fu.rms.dto.ImportMaterialDetailDto;
import fu.rms.dto.ImportMaterialDto;
import fu.rms.dto.MaterialDto;
import fu.rms.dto.SupplierDto;
import fu.rms.dto.WarehouseDto;
import fu.rms.request.ImportExistMaterialRequest;
import fu.rms.request.ImportExistRequest;
import fu.rms.respone.SearchRespone;
import fu.rms.service.IImportService;

public class ImportControllerTest {

	private MockMvc mockMvc;

	@Mock
	private IImportService importService;

	@InjectMocks
	private ImportController importController;

	private List<ImportDto> imports = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(importController).build();

		ImportDto import1 = new ImportDto();
		import1.setImportId(1L);
		import1.setImportCode("IMPORT");
		import1.setTotalAmount(10000D);
		import1.setComment("NhanNTK");
		import1.setCreatedBy("NhanNTK");
		import1.setCreatedDate("20/3/20");
		import1.setLastModifiedBy("NhanNTK");
		import1.setLastModifiedDate("20/4/20");

		ImportDto import2 = new ImportDto();
		import2.setImportId(1L);
		import2.setImportCode("IMPORT2");
		import2.setTotalAmount(10000D);
		import2.setComment("DUCNV");
		import2.setCreatedBy("DUCNV");
		import2.setCreatedDate("20/3/20");
		import2.setLastModifiedBy("DUCNV");
		import2.setLastModifiedDate("20/4/20");

		SupplierDto supplier = new SupplierDto();
		supplier.setSupplierId(1L);
		supplier.setSupplierName("Đơn vị Trung Văn");
		supplier.setPhone("0824917021");

		ImportMaterialDto importMaterialDto = new ImportMaterialDto();
		importMaterialDto.setImportMaterialId(1L);
		importMaterialDto.setQuantityImport(100D);
		importMaterialDto.setPrice(1000D);
		importMaterialDto.setSumPrice(1000D);
		importMaterialDto.setWarehouse(new WarehouseDto());
		importMaterialDto.setMaterial(new MaterialDto());

		List<ImportMaterialDto> importMaterials = new ArrayList<>();
		importMaterials.add(importMaterialDto);

		import1.setSupplier(supplier);
		import1.setImportMaterials(importMaterials);

		import2.setSupplier(supplier);
		import2.setImportMaterials(importMaterials);

		imports = new ArrayList<>();
		imports.add(import1);
		imports.add(import2);

	}

	@Test
	@DisplayName("Create Import")
	public void testWhenCreate() throws Exception {

		// expect
		ImportExistMaterialRequest importExistMaterialRequest1 = new ImportExistMaterialRequest(); // importExistMaterialRequest1
		importExistMaterialRequest1.setQuantityImport(100D);
		importExistMaterialRequest1.setUnitPrice(50000D);
		importExistMaterialRequest1.setSumPrice(5000000D);
		importExistMaterialRequest1.setExpireDate(3);
		importExistMaterialRequest1.setWarehouseId(2L);
		importExistMaterialRequest1.setMaterialId(2L);

		ImportExistMaterialRequest importExistMaterialRequest2 = new ImportExistMaterialRequest(); // importExistMaterialRequest2
		importExistMaterialRequest2.setQuantityImport(100D);
		importExistMaterialRequest2.setUnitPrice(50000D);
		importExistMaterialRequest2.setSumPrice(5000000D);
		importExistMaterialRequest2.setExpireDate(3);
		importExistMaterialRequest2.setWarehouseId(3L);
		importExistMaterialRequest2.setMaterialId(3L);

		List<ImportExistMaterialRequest> importExistMaterialRequests = new ArrayList<>();
		importExistMaterialRequests.add(importExistMaterialRequest1);
		importExistMaterialRequests.add(importExistMaterialRequest2);

		ImportExistRequest importExistRequest = new ImportExistRequest(); // importExistRequest
		importExistRequest.setImportCode("ImportNhanNTK");
		importExistRequest.setTotalAmount(100000D);
		importExistRequest.setComment("NhanNTK");
		importExistRequest.setSupplierId(5L);
		importExistRequest.setImportMaterials(importExistMaterialRequests);

		ImportDto importDto = imports.get(0);

		// when
		when(importService.importExistInventory(Mockito.any(ImportExistRequest.class))).thenReturn(importDto);

		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(post("/imports/existInventory").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(importExistRequest))).andExpect(status().isOk());

	}

	@Test
	@DisplayName("Search Import")
	public void testWhenSearch() throws Exception {
		SearchRespone<ImportDto> searchResponeExpect = new SearchRespone<>();
		searchResponeExpect.setPage(1);
		searchResponeExpect.setResult(imports);
		searchResponeExpect.setTotalPages(1);

		// when
		when(importService.search(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(searchResponeExpect);

		// test
		mockMvc.perform(get("/imports/search")).andExpect(status().isOk());

	}

	@Test
	@DisplayName("get ImportMaterialDetail")
	public void testWhenGetImportMaterialDetail() throws Exception {
		// expect
		ImportMaterialDetailDto importMaterialDetailExpect = new ImportMaterialDetailDto() {

			@Override
			public String getWarehouseName() {
				// TODO Auto-generated method stub
				return "Kho Đồ Khô";
			}

			@Override
			public Double getUnitPrice() {
				// TODO Auto-generated method stub
				return 100000D;
			}

			@Override
			public String getUnit() {
				// TODO Auto-generated method stub
				return "Kg";
			}

			@Override
			public Double getTotalAmount() {
				// TODO Auto-generated method stub
				return 1000000D;
			}

			@Override
			public String getSupplierName() {
				// TODO Auto-generated method stub
				return "Đơn vị Bùi Đức";
			}

			@Override
			public Double getQuantity() {
				// TODO Auto-generated method stub
				return 10D;
			}

			@Override
			public String getMaterialName() {
				// TODO Auto-generated method stub
				return "Thịt Gà";
			}

			@Override
			public String getImportCode() {
				// TODO Auto-generated method stub
				return "ImportCode1";
			}

			@Override
			public String getExpireDate() {
				// TODO Auto-generated method stub
				return "20/10/2020";
			}

			@Override
			public String getCreatedDate() {
				// TODO Auto-generated method stub
				return "20/9/2020";
			}
		};
		
		when(importService.getImportMaterialDetailByImportMaterialId(Mockito.anyLong())).thenReturn(importMaterialDetailExpect);
		
		// test
		mockMvc.perform(get("/imports/import-material-detail/{id}",1)).andExpect(status().isOk());
		
	}
}
