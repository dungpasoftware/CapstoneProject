package fu.rms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import fu.rms.AbstractSpringBootTest;
import fu.rms.constant.StatusConstant;
import fu.rms.dto.ImportAndExportDto;
import fu.rms.dto.MaterialDto;
import fu.rms.entity.Dish;
import fu.rms.entity.GroupMaterial;
import fu.rms.entity.Import;
import fu.rms.entity.Material;
import fu.rms.entity.Option;
import fu.rms.entity.Quantifier;
import fu.rms.entity.QuantifierOption;
import fu.rms.entity.Status;
import fu.rms.entity.Supplier;
import fu.rms.entity.Warehouse;
import fu.rms.exception.NotFoundException;
import fu.rms.repository.DishRepository;
import fu.rms.repository.GroupMaterialRepository;
import fu.rms.repository.ImportRepository;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.repository.SupplierRepository;
import fu.rms.repository.WarehouseRepository;
import fu.rms.request.ImportMaterialRequest;
import fu.rms.request.ImportRequest;
import fu.rms.request.MaterialRequest;
import fu.rms.respone.SearchRespone;

public class MaterialServiceTest extends AbstractSpringBootTest {

	@Autowired
	private MaterialService materialService;

	@MockBean
	private MaterialRepository materialRepo;

	@MockBean
	private GroupMaterialRepository groupRepo;

	@MockBean
	private StatusRepository statusRepo;

	@MockBean
	private ImportRepository importRepo;

	@MockBean
	private SupplierRepository supplierRepo;

	@MockBean
	private WarehouseRepository warehouseRepo;

	@MockBean
	private DishRepository dishRepo;

	@MockBean
	private OptionRepository optionRepo;

	private List<Material> materials;

	@BeforeEach
	public void setUp() {

		Status status = new Status();
		status.setStatusId(StatusConstant.STATUS_MATERIAL_AVAILABLE);
		status.setStatusName("Status");
		status.setStatusDescription("Status Material");
		status.setStatusValue("AVAILABLE");

		GroupMaterial groupMaterial = new GroupMaterial();
		groupMaterial.setGroupId(1L);
		groupMaterial.setGroupName("Đồ Tươi");

		Material material1 = new Material(); // material1
		material1.setMaterialId(1L);
		material1.setMaterialCode("THIT-BO");
		material1.setMaterialName("Thịt Bò");
		material1.setUnit("Kg");
		material1.setUnitPrice(100000D);
		material1.setTotalPrice(1000000D);
		material1.setTotalImport(100D);
		material1.setTotalExport(90D);
		material1.setRemain(10D);
		material1.setRemainNotification(10D);
		material1.setStatus(status);
		material1.setGroupMaterial(groupMaterial);
		material1.setImportMaterials(null);
		material1.setQuantifiers(null);
		material1.setQuantifierOptions(null);
		material1.setCreatedBy("NhanNTK");
		material1.setCreatedDate(LocalDateTime.now());
		material1.setLastModifiedBy("NhanNTK");
		material1.setLastModifiedDate(LocalDateTime.now());

		Material material2 = new Material(); // material2
		material2.setMaterialId(2L);
		material2.setMaterialCode("THIT-GA");
		material2.setMaterialName("Thịt Gà");
		material2.setUnit("Kg");
		material2.setUnitPrice(100000D);
		material2.setTotalPrice(1000000D);
		material2.setTotalImport(100D);
		material2.setTotalExport(90D);
		material2.setRemain(10D);
		material2.setRemainNotification(10D);
		material2.setStatus(status);
		material2.setGroupMaterial(groupMaterial);
		material2.setImportMaterials(null);
		material2.setQuantifiers(null);
		material2.setQuantifierOptions(null);
		material2.setCreatedBy("NhanNTK");
		material2.setCreatedDate(LocalDateTime.now());
		material2.setLastModifiedBy("NhanNTK");
		material2.setLastModifiedDate(LocalDateTime.now());

		materials = new ArrayList<>();
		materials.add(material1);
		materials.add(material2);

	}

	@Test
	@DisplayName("Get All Material")
	public void testWhenGetAll() {
		// expect
		List<Material> materialsExpect = materials;

		when(materialRepo.findByStatusId(Mockito.anyLong())).thenReturn(materialsExpect);

		// actual

		List<MaterialDto> materialsActual = materialService.getAll();

		// test

		assertThat(materialsExpect.size()).isEqualTo(materialsActual.size());

	}

	@Test
	@DisplayName("Get By Id")
	public void testWhenGetById() {
		// expect
		Material materialExpect = materials.get(0);

		// when
		when(materialRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(materialExpect));

		// actual
		MaterialDto materialActual = materialService.getById(5L);

		// test
		assertThat(materialActual.getMaterialId()).isEqualTo(materialExpect.getMaterialId());
		assertThat(materialActual.getMaterialCode()).isEqualTo(materialExpect.getMaterialCode());
		assertThat(materialActual.getMaterialName()).isEqualTo(materialExpect.getMaterialName());
		assertThat(materialActual.getUnit()).isEqualTo(materialExpect.getUnit());
		assertThat(materialActual.getUnitPrice()).isEqualTo(materialExpect.getUnitPrice());
		assertThat(materialActual.getTotalPrice()).isEqualTo(materialExpect.getTotalPrice());
		assertThat(materialActual.getTotalImport()).isEqualTo(materialExpect.getTotalImport());
		assertThat(materialActual.getTotalExport()).isEqualTo(materialExpect.getTotalExport());
		assertThat(materialActual.getRemain()).isEqualTo(materialExpect.getRemain());
		assertThat(materialActual.getRemainNotification()).isEqualTo(materialExpect.getRemainNotification());
		assertThat(materialActual.getGroupMaterial()).isNotNull();

	}

	@Test
	@DisplayName("Get By Id Not Found")
	public void testWhenGetByIdNotFound() {

		// when
		when(materialRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		// test
		assertThrows(NotFoundException.class, () -> materialService.getById(3L));
	}

	@Test
	@DisplayName("Update Material")
	public void testWhenUpdate() {
		// expect
		MaterialRequest materialRequest = new MaterialRequest(); // material request
		materialRequest.setMaterialCode("GA");
		materialRequest.setMaterialName("GÀ");
		materialRequest.setUnit("Kg");
		materialRequest.setUnitPrice(120000D);
		materialRequest.setRemainNotification(50D);
		materialRequest.setGroupMaterialId(5L);

		Material materialExpect = materials.get(0); // material expect

		GroupMaterial groupMaterialExpect = new GroupMaterial(); // group material
		groupMaterialExpect.setGroupId(5L);
		groupMaterialExpect.setGroupName("Đồ Lạnh");

		Quantifier quantifier1 = new Quantifier(); // quantifier 1
		quantifier1.setQuantifierId(1L);
		quantifier1.setQuantity(0.1D);
		quantifier1.setCost(10000D);
		quantifier1.setDescription("Nguyên liệu 1");
		quantifier1.setMaterial(materialExpect);

		Quantifier quantifier2 = new Quantifier(); // quantifier 1
		quantifier2.setQuantifierId(1L);
		quantifier2.setQuantity(0.1D);
		quantifier2.setCost(10000D);
		quantifier2.setDescription("Nguyên liệu 2");
		quantifier2.setMaterial(materials.get(1));

		Dish dishExpect1 = new Dish(); // dish 1
		dishExpect1.setDishId(1L);
		dishExpect1.setDishCode("PHO-BO");
		dishExpect1.setDishName("Phở bò");
		dishExpect1.setDishUnit("Bát");
		dishExpect1.setDefaultPrice(30000D);
		dishExpect1.setCost(10500D);
		dishExpect1.setDishCost(20000D);
		dishExpect1.setDescription("Đây là Phở bò");
		dishExpect1.setTimeComplete(150F);
		dishExpect1.setImageUrl("hinh1.png");
		dishExpect1.setTypeReturn(true);
		dishExpect1.setQuantifiers(Arrays.asList(quantifier1, quantifier2));

		Dish dishExpect2 = new Dish(); // dish 2
		dishExpect2.setDishId(1L);
		dishExpect2.setDishCode("MY-BO");
		dishExpect2.setDishName("Mỳ bò");
		dishExpect2.setDishUnit("Bát");
		dishExpect2.setDefaultPrice(30000D);
		dishExpect2.setCost(10500D);
		dishExpect2.setDishCost(20000D);
		dishExpect2.setDescription("Đây là Mỳ bò");
		dishExpect2.setTimeComplete(120F);
		dishExpect2.setImageUrl("hinh2.png");
		dishExpect2.setTypeReturn(false);
		dishExpect2.setQuantifiers(Arrays.asList(quantifier1));

		List<Dish> dishesExpect = new ArrayList<>(); // dishesExpect
		dishesExpect.add(dishExpect1);
		dishesExpect.add(dishExpect2);

		QuantifierOption quantifierOption1 = new QuantifierOption(); // quantifier option 1
		quantifierOption1.setQuantifierOptionId(1L);
		quantifierOption1.setQuantity(0.1D);
		quantifierOption1.setCost(10000D);
		quantifierOption1.setDescription("Nguyên liệu 1");
		quantifierOption1.setMaterial(materialExpect);

		QuantifierOption quantifierOption2 = new QuantifierOption(); // quantifier option 1
		quantifierOption2.setQuantifierOptionId(1L);
		quantifierOption2.setQuantity(0.1D);
		quantifierOption2.setCost(10000D);
		quantifierOption2.setDescription("Nguyên liệu 2");
		quantifierOption2.setMaterial(materials.get(1));

		Option optionExpect1 = new Option(); // option 1
		optionExpect1.setOptionId(1L);
		optionExpect1.setOptionName("Thêm bò");
		optionExpect1.setOptionType("MONEY");
		optionExpect1.setUnit("Bát nhỏ");
		optionExpect1.setPrice(15000D);
		optionExpect1.setCost(10000D);
		optionExpect1.setOptionCost(12000D);
		optionExpect1.setQuantifierOptions(new ArrayList<>(Arrays.asList(quantifierOption1, quantifierOption2)));

		Option optionExpect2 = new Option(); // option 2
		optionExpect2.setOptionId(2L);
		optionExpect2.setOptionName("Thêm Phở");
		optionExpect2.setOptionType("MONEY");
		optionExpect2.setUnit("Dĩa nhỏ");
		optionExpect2.setPrice(1000D);
		optionExpect2.setCost(500D);
		optionExpect2.setOptionCost(1500D);
		optionExpect2.setQuantifierOptions(new ArrayList<>(Arrays.asList(quantifierOption1)));

		List<Option> optionsExpect = new ArrayList<>();
		optionsExpect.add(optionExpect1);
		optionsExpect.add(optionExpect2);

		when(materialRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(materialExpect));
		when(groupRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(groupMaterialExpect));
		when(dishRepo.findByMaterialId(Mockito.anyLong())).thenReturn(dishesExpect);
		when(dishRepo.save(Mockito.any(Dish.class))).thenReturn(dishExpect1).thenReturn(dishExpect2);
		when(optionRepo.findByMaterialId(Mockito.anyLong())).thenReturn(optionsExpect);
		when(optionRepo.save(Mockito.any(Option.class))).thenReturn(optionExpect1).thenReturn(optionExpect2);
		when(materialRepo.save(Mockito.any(Material.class))).thenReturn(materialExpect);

		// actual
		MaterialDto materialActual = materialService.update(materialRequest, 1L);

		assertThat(materialActual).isNotNull();

	}

	@Test
	@DisplayName("Update Material Not Found")
	public void testWhenUpdateNotFound() {
		// expect
		MaterialRequest materialRequest = new MaterialRequest(); // material request
		materialRequest.setMaterialCode("GA");
		materialRequest.setMaterialName("GÀ");
		materialRequest.setUnit("Kg");
		materialRequest.setUnitPrice(120000D);
		materialRequest.setRemainNotification(50D);
		materialRequest.setGroupMaterialId(5L);
		// when
		when(materialRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		// test
		
		assertThrows(NotFoundException.class, () -> materialService.update(materialRequest, 3L));
	}
	
	@Test
	@DisplayName("Create Material")
	public void testWhenCreate() {
		
		// expect
		
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
		
		Material materialExpect2 = materials.get(1); // material 2
		
		Status statusExpect = new Status(); // status
		statusExpect.setStatusId(StatusConstant.STATUS_MATERIAL_AVAILABLE);
		statusExpect.setStatusName("Status");
		statusExpect.setStatusDescription("Status Material");
		statusExpect.setStatusValue("AVAILABLE");
		
		GroupMaterial groupMaterialExpect = new GroupMaterial(); // group material
		groupMaterialExpect.setGroupId(5L);
		groupMaterialExpect.setGroupName("Đồ Lạnh");
		
		Import importExpect = new Import(); // import
		importExpect.setImportId(1L);
		importExpect.setImportCode("ImportNhanNTK");
		importExpect.setTotalAmount(500000D);
		importExpect.setComment("Nhập hàng");
		importExpect.setSupplier(null);
		importExpect.setImportMaterials(null);
		importExpect.setCreatedBy("NhanNTK");
		importExpect.setCreatedDate(LocalDateTime.now());
		importExpect.setLastModifiedBy("NhanNTK");
		importExpect.setLastModifiedDate(LocalDateTime.now().minusDays(2));
		
		Supplier supplierExpect = new Supplier(); // supplier
		supplierExpect.setSupplierId(1L);
		supplierExpect.setSupplierName("Đơn vị trung văn");
		supplierExpect.setPhone("082434331");
		
		Warehouse warehouseExpect = new Warehouse(); // warehouse
		warehouseExpect.setWarehouseId(1L);
		warehouseExpect.setName("Kho Đông Lạnh");
		
		
		// when
		when(materialRepo.findByMaterialCode(Mockito.anyString())).thenReturn(null);
		when(statusRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(statusExpect));
		when(groupRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(groupMaterialExpect));
		when(materialRepo.save(Mockito.any(Material.class))).thenReturn(materialExpect2);
		when(importRepo.findByImportCode(Mockito.anyString()))
		.thenReturn(null);
		when(supplierRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(supplierExpect));
		when(warehouseRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(warehouseExpect));
		when(importRepo.save(Mockito.any(Import.class))).thenReturn(importExpect);
		
		// actual
		MaterialDto materialActual = materialService.create(importRequest);
		
		// test
		assertThat(materialActual).isNotNull();
		
		
		
	}
	
	@Test
	@DisplayName("Get Import And Export")
	public void testWhenGetImportAndExport() {
		
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
		
		ImportAndExportDto importAndExport2= new ImportAndExportDto() {
			
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
		
		List<ImportAndExportDto> importAndExportsExpect = new ArrayList<>();
		importAndExportsExpect.add(importAndExport1);
		importAndExportsExpect.add(importAndExport2);
		
		// when
		when(materialService.getImportAndExportById(Mockito.anyLong())).thenReturn(importAndExportsExpect);
		
		// actual
		List<ImportAndExportDto> importAndExportsActual = materialService.getImportAndExportById(Mockito.anyLong());
		// test
		assertThat(importAndExportsActual.size()).isEqualTo(importAndExportsExpect.size());
	}
	
	@Test
	@DisplayName("Search Material All")
	public void testWhenSearchAll() {
		// expect
		
		Page<Material> pageMaterialExpect = new Page<Material>() {
			
			@Override
			public Iterator<Material> iterator() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Pageable previousPageable() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Pageable nextPageable() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isLast() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isFirst() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasPrevious() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasContent() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getNumberOfElements() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getNumber() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public List<Material> getContent() {
				// TODO Auto-generated method stub
				return materials;
			}
			
			@Override
			public <U> Page<U> map(Function<? super Material, ? extends U> converter) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getTotalPages() {
				// TODO Auto-generated method stub
				return 1;
			}
			
			@Override
			public long getTotalElements() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		
		String materialCodeExpect = null;
		Long groupIdExpect = null;
		Integer pageExpect = null;
		
		when(materialRepo.findByStatusId(Mockito.anyLong(), Mockito.any(PageRequest.class))).thenReturn(pageMaterialExpect);
		
		SearchRespone<MaterialDto> searchResponeActual = materialService.search(materialCodeExpect, groupIdExpect, pageExpect);
		
		assertThat(searchResponeActual.getResult().size()).isEqualTo(pageMaterialExpect.getContent().size());
		
		
	}
	
	@Test
	@DisplayName("Search Material")
	public void testWhenSearch() {
		
Page<Material> pageMaterialExpect = new Page<Material>() {
			
			@Override
			public Iterator<Material> iterator() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Pageable previousPageable() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Pageable nextPageable() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isLast() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isFirst() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasPrevious() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasContent() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getNumberOfElements() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getNumber() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public List<Material> getContent() {
				// TODO Auto-generated method stub
				return materials;
			}
			
			@Override
			public <U> Page<U> map(Function<? super Material, ? extends U> converter) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getTotalPages() {
				// TODO Auto-generated method stub
				return 1;
			}
			
			@Override
			public long getTotalElements() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		
		String materialCodeExpect = "PHO-BO";
		Long groupIdExpect = 2L;
		Integer pageExpect = 1;
		
		when(materialRepo.findByCriteria(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong(), Mockito.any(PageRequest.class))).thenReturn(pageMaterialExpect);
		
		SearchRespone<MaterialDto> searchResponeActual = materialService.search(materialCodeExpect, groupIdExpect, pageExpect);
		
		assertThat(searchResponeActual.getResult().size()).isEqualTo(pageMaterialExpect.getContent().size());
	}
	

}
