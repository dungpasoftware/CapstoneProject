package fu.rms.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import fu.rms.dto.GroupMaterialDto;
import fu.rms.dto.ImportAndExportDto;
import fu.rms.dto.MaterialDto;
import fu.rms.request.ImportMaterialRequest;
import fu.rms.request.ImportRequest;
import fu.rms.request.MaterialRequest;
import fu.rms.respone.SearchRespone;
import fu.rms.service.IMaterialService;

public class MaterialControllerTest {

	private MockMvc mockMvc;

	@Mock
	private IMaterialService materialService;

	@InjectMocks
	private MaterialController materialController;

	private List<MaterialDto> materials;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(materialController).build();

		MaterialDto material1 = new MaterialDto();
		material1.setMaterialId(3L);
		material1.setMaterialCode("THIT-BO");
		material1.setMaterialName("Thịt bò");
		material1.setUnit("Kg");
		material1.setUnitPrice(1000D);
		material1.setTotalPrice(10000D);
		material1.setTotalImport(100D);
		material1.setTotalExport(100D);
		material1.setRemain(100D);
		material1.setRemainNotification(10D);

		MaterialDto material2 = new MaterialDto();
		material2.setMaterialId(3L);
		material2.setMaterialCode("THIT-GA");
		material2.setMaterialName("Thịt gà");
		material2.setUnit("Kg");
		material2.setUnitPrice(1000D);
		material2.setTotalPrice(10000D);
		material2.setTotalImport(100D);
		material2.setTotalExport(100D);
		material2.setRemain(100D);
		material2.setRemainNotification(10D);

		GroupMaterialDto groupMaterial = new GroupMaterialDto();
		groupMaterial.setGroupId(1L);
		groupMaterial.setGroupName("Thịt");

		material1.setGroupMaterial(groupMaterial);
		material2.setGroupMaterial(groupMaterial);

		materials = new ArrayList<>();
		materials.add(material1);
		materials.add(material2);
	}

	@Test
	@DisplayName("Get All Material")
	public void testWhenGetAll() throws Exception {

		// expect
		List<MaterialDto> materialsExpect = materials;

		// when
		when(materialService.getAll()).thenReturn(materialsExpect);

		// test
		mockMvc.perform(get("/materials")).andExpect(status().isOk());

		verify(materialService, times(1)).getAll();
	}

	@Test
	@DisplayName("Get Material By Id")
	public void testWhenGetById() throws Exception {

		// exepect
		MaterialDto materialExpect = materials.get(0);

		// when
		when(materialService.getById(Mockito.anyLong())).thenReturn(materialExpect);

		// test
		mockMvc.perform(get("/materials/{id}", 5)).andExpect(status().isOk());

		verify(materialService, times(1)).getById(5L);
	}

	@Test
	@DisplayName("Create Material")
	public void testWhenCreate() throws Exception {

		MaterialRequest materialRequest = new MaterialRequest(); // material request
		materialRequest.setMaterialCode("THIT-GA");
		materialRequest.setMaterialName("Thịt Gà");
		materialRequest.setUnit("Kg");
		materialRequest.setUnitPrice(120000D);
		materialRequest.setRemainNotification(50D);
		materialRequest.setGroupMaterialId(5L);

		ImportMaterialRequest importMaterialRequest = new ImportMaterialRequest(); // importMaterial request
		importMaterialRequest.setQuantityImport(10D);
		importMaterialRequest.setSumPrice(1200000D);
		importMaterialRequest.setExpireDate(30);
		importMaterialRequest.setWarehouseId(1L);
		importMaterialRequest.setMaterial(materialRequest);

		ImportRequest importRequest = new ImportRequest(); // import request
		importRequest.setImportCode("Import");
		importRequest.setTotalAmount(1200000D);
		importRequest.setComment("Nhập");
		importRequest.setSupplierId(1L);
		importRequest.setImportMaterial(importMaterialRequest);

		MaterialDto materialExpect = materials.get(0);

		// when
		when(materialService.create(Mockito.any(ImportRequest.class))).thenReturn(materialExpect);

		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(post("/materials").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(importRequest))).andExpect(status().isOk());

	}

	@Test
	@DisplayName("Create Material")
	public void testWhenUpdate() throws Exception {

		// expect
		MaterialRequest materialRequest = new MaterialRequest(); // material request
		materialRequest.setMaterialCode("GA");
		materialRequest.setMaterialName("GÀ");
		materialRequest.setUnit("Kg");
		materialRequest.setUnitPrice(120000D);
		materialRequest.setRemainNotification(50D);
		materialRequest.setGroupMaterialId(5L);

		MaterialDto materialExpect = materials.get(0);

		// when
		when(materialService.update(Mockito.any(MaterialRequest.class), Mockito.anyLong())).thenReturn(materialExpect);

		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(put("/materials/{id}", 3).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(materialExpect))).andExpect(status().isOk());

	}
	
	@Test
	@DisplayName("Search Material")
	public void testWhenSearchMaterial() throws Exception {
		
		SearchRespone<MaterialDto> searchResponeExpect = new SearchRespone<>();
		searchResponeExpect.setPage(1);
		searchResponeExpect.setResult(materials);
		searchResponeExpect.setTotalPages(1);

		// when
		when(materialService.search(Mockito.anyString(), Mockito.anyLong(),Mockito.anyInt())).thenReturn(searchResponeExpect);

		// test
		mockMvc.perform(get("/materials/search")).andExpect(status().isOk());
		
	}
	
	@Test
	@DisplayName("Get Import And Export")
	public void testWhenGetImportAndExportById() throws Exception {
		
		ImportAndExportDto importAndExport1 = new ImportAndExportDto() {

			@Override
			public String getWarehouseName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Double getUnitPrice() {
				// TODO Auto-generated method stub
				return 100000D;
			}

			@Override
			public String getType() {
				// TODO Auto-generated method stub
				return "Export";
			}

			@Override
			public Double getTotalAmount() {
				// TODO Auto-generated method stub
				return 1000000D;
			}

			@Override
			public String getSupplierName() {
				// TODO Auto-generated method stub
				return "Đơn vị trung văn";
			}

			@Override
			public Double getQuantity() {
				// TODO Auto-generated method stub
				return 10D;
			}

			@Override
			public String getCreatedDate() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getCode() {
				// TODO Auto-generated method stub
				return "Export";
			}
		};
		
		List<ImportAndExportDto> importAndExports = new ArrayList<>();
		importAndExports.add(importAndExport1);
		
		// when
		when(materialService.getImportAndExportById(Mockito.anyLong())).thenReturn(importAndExports);
		
		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(get("/materials/import-export/{id}", 1).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(importAndExports))).andExpect(status().isOk());
		
		

	}
}
