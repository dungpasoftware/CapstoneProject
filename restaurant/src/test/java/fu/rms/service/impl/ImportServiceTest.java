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
import fu.rms.dto.ImportDto;
import fu.rms.dto.ImportMaterialDetailDto;
import fu.rms.entity.Dish;
import fu.rms.entity.GroupMaterial;
import fu.rms.entity.Import;
import fu.rms.entity.ImportMaterial;
import fu.rms.entity.Material;
import fu.rms.entity.Option;
import fu.rms.entity.Quantifier;
import fu.rms.entity.QuantifierOption;
import fu.rms.entity.Supplier;
import fu.rms.entity.Warehouse;
import fu.rms.exception.DuplicateException;
import fu.rms.repository.DishRepository;
import fu.rms.repository.ImportRepository;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.SupplierRepository;
import fu.rms.repository.WarehouseRepository;
import fu.rms.request.ImportExistMaterialRequest;
import fu.rms.request.ImportExistRequest;
import fu.rms.respone.SearchRespone;

public class ImportServiceTest extends AbstractSpringBootTest {

	@Autowired
	private ImportService importService;

	@MockBean
	private ImportRepository importRepo;

	@MockBean
	private MaterialRepository materialRepo;

	@MockBean
	private WarehouseRepository warehouseRepo;

	@MockBean
	private SupplierRepository supplierRepo;

	@MockBean
	private DishRepository dishRepo;

	@MockBean
	private OptionRepository optionRepo;

	private List<Import> imports;

	@BeforeEach
	public void setUp() {

		Supplier supplier = new Supplier(); // supplier
		supplier.setSupplierId(1L);
		supplier.setSupplierName("Đơn vị Văn Đức");
		supplier.setPhone("021231211");

		Warehouse warehouse = new Warehouse(); // warehouse
		warehouse.setWarehouseId(2L);
		warehouse.setName("Nhà kho khô");

		GroupMaterial groupMaterial = new GroupMaterial(); // group material
		groupMaterial.setGroupId(1L);
		groupMaterial.setGroupName("Đồ Tươi");

		Material material1 = new Material(); // material 1
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
		material1.setGroupMaterial(groupMaterial);
		material1.setImportMaterials(null);
		material1.setQuantifiers(null);
		material1.setQuantifierOptions(null);
		material1.setCreatedBy("NhanNTK");
		material1.setCreatedDate(LocalDateTime.now());
		material1.setLastModifiedBy("NhanNTK");
		material1.setLastModifiedDate(LocalDateTime.now());

		ImportMaterial importMaterial1 = new ImportMaterial(); // import material 1
		importMaterial1.setImportMaterialId(1L);
		importMaterial1.setQuantityImport(100D);
		importMaterial1.setUnitPrice(50000D);
		importMaterial1.setSumPrice(5000000D);
		importMaterial1.setExpireDate(LocalDateTime.now());
		importMaterial1.setWarehouse(warehouse);
		importMaterial1.setMaterial(material1);

		Material material2 = new Material(); // material 2
		material2.setMaterialId(1L);
		material2.setMaterialCode("THIT-BO");
		material2.setMaterialName("Thịt Bò");
		material2.setUnit("Kg");
		material2.setUnitPrice(100000D);
		material2.setTotalPrice(1000000D);
		material2.setTotalImport(100D);
		material2.setTotalExport(90D);
		material2.setRemain(10D);
		material2.setRemainNotification(10D);
		material2.setGroupMaterial(groupMaterial);
		material2.setImportMaterials(null);
		material2.setQuantifiers(null);
		material2.setQuantifierOptions(null);
		material2.setCreatedBy("NhanNTK");
		material2.setCreatedDate(LocalDateTime.now());
		material2.setLastModifiedBy("NhanNTK");
		material2.setLastModifiedDate(LocalDateTime.now());

		ImportMaterial importMaterial2 = new ImportMaterial(); // import material 2
		importMaterial2.setImportMaterialId(1L);
		importMaterial2.setQuantityImport(100D);
		importMaterial2.setUnitPrice(50000D);
		importMaterial2.setSumPrice(5000000D);
		importMaterial2.setExpireDate(LocalDateTime.now());
		importMaterial2.setWarehouse(warehouse);
		importMaterial2.setMaterial(material2);

		List<ImportMaterial> importMaterials = new ArrayList<>();
		importMaterials.add(importMaterial1);
		importMaterials.add(importMaterial2);

		Import import1 = new Import();
		import1.setImportId(1L);
		import1.setImportCode("Import1");
		import1.setTotalAmount(500000D);
		import1.setComment("Nhập kho");
		import1.setSupplier(supplier);
		import1.setImportMaterials(importMaterials);

		Import import2 = new Import();
		import2.setImportId(2L);
		import2.setImportCode("Import2");
		import2.setTotalAmount(400000D);
		import2.setComment("Nhập kho");
		import2.setSupplier(supplier);
		import2.setImportMaterials(importMaterials);

		imports = new ArrayList<>();
		imports.add(import1);
		imports.add(import2);

	}

	@Test
	@DisplayName("Import Exist Inventory")
	public void testWhenImportExistInventory() {

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

		Import importExpect2 = imports.get(1); // importExpect 2

		Supplier supplierExpect = new Supplier(); // supplier expect
		supplierExpect.setSupplierId(1L);
		supplierExpect.setSupplierName("Đơn vị Văn Đức");
		supplierExpect.setPhone("021231211");

		Warehouse warehouseExpect = new Warehouse(); // warehouse expect
		warehouseExpect.setWarehouseId(2L);
		warehouseExpect.setName("Nhà kho khô");

		Material materialExpect = new Material(); // material expect 1
		materialExpect.setMaterialId(1L);
		materialExpect.setMaterialCode("THIT-BO");
		materialExpect.setMaterialName("Thịt Bò");
		materialExpect.setUnit("Kg");
		materialExpect.setUnitPrice(100000D);
		materialExpect.setTotalPrice(1000000D);
		materialExpect.setTotalImport(100D);
		materialExpect.setTotalExport(90D);
		materialExpect.setRemain(10D);
		materialExpect.setRemainNotification(10D);
		materialExpect.setGroupMaterial(null);
		materialExpect.setImportMaterials(null);
		materialExpect.setQuantifiers(null);
		materialExpect.setQuantifierOptions(null);
		materialExpect.setCreatedBy("NhanNTK");
		materialExpect.setCreatedDate(LocalDateTime.now());
		materialExpect.setLastModifiedBy("NhanNTK");
		materialExpect.setLastModifiedDate(LocalDateTime.now());

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
		quantifier2.setMaterial(materialExpect);

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
		quantifierOption2.setMaterial(materialExpect);

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

		when(importRepo.findByImportCode(Mockito.anyString())).thenReturn(null);

		when(supplierRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(supplierExpect));
		when(warehouseRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(warehouseExpect));
		when(materialRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(materialExpect));
		when(importRepo.save(Mockito.any(Import.class))).thenReturn(importExpect2);
		when(dishRepo.findByMaterialId(Mockito.anyLong())).thenReturn(dishesExpect);
		when(dishRepo.save(Mockito.any(Dish.class))).thenReturn(dishesExpect.get(0));
		when(optionRepo.findByMaterialId(Mockito.anyLong())).thenReturn(optionsExpect);
		when(optionRepo.save(Mockito.any(Option.class))).thenReturn(optionsExpect.get(0));

		ImportDto importActual = importService.importExistInventory(importExistRequest);

		assertThat(importActual).isNotNull();

	}

	@Test
	@DisplayName("Import Exist Inventory Duplicate Code")
	public void whenImportExistInventoryDuplicateCode() {

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
		
		Import importExpect = imports.get(0); // importExpect 1
		
		// when
		when(importRepo.findByImportCode(Mockito.anyString())).thenReturn(importExpect);
		
		// test
		assertThrows(DuplicateException.class, () -> importService.importExistInventory(importExistRequest));
	}

	@Test
	@DisplayName("Search Import All")
	public void testWhenSearchAll() {

		Page<Import> pageImportExpect = new Page<Import>() {

			@Override
			public Iterator<Import> iterator() {
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
			public List<Import> getContent() {
				// TODO Auto-generated method stub
				return imports;
			}

			@Override
			public <U> Page<U> map(Function<? super Import, ? extends U> converter) {
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

		Long supplierIdExpect = null;
		String dateFromExpect = null;
		String dateToExpect = null;
		Integer pageExpect = null;

		when(importRepo.findAll(Mockito.any(PageRequest.class))).thenReturn(pageImportExpect);

		// actual
		SearchRespone<ImportDto> searchResponeActual = importService.search(supplierIdExpect, dateFromExpect,
				dateToExpect, pageExpect);

		assertThat(searchResponeActual.getResult().size()).isEqualTo(pageImportExpect.getContent().size());

	}

	@Test
	@DisplayName("Search Import")
	public void testWhenSearch() {

		Page<Import> pageImportExpect = new Page<Import>() {

			@Override
			public Iterator<Import> iterator() {
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
			public List<Import> getContent() {
				// TODO Auto-generated method stub
				return imports;
			}

			@Override
			public <U> Page<U> map(Function<? super Import, ? extends U> converter) {
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

		Long supplierIdExpect = 1L;
		String dateFromExpect = null;
		String dateToExpect = null;
		Integer pageExpect = 2;

		when(importRepo.findByCriteria(Mockito.anyLong(), Mockito.any(), Mockito.any(), Mockito.any(PageRequest.class)))
				.thenReturn(pageImportExpect);

		// actual
		SearchRespone<ImportDto> searchResponeActual = importService.search(supplierIdExpect, dateFromExpect,
				dateToExpect, pageExpect);

		assertThat(searchResponeActual.getResult().size()).isEqualTo(pageImportExpect.getContent().size());

	}

	@Test
	@DisplayName("Get Import Material Detail")
	public void testWhenImportMaterialDetail() {
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

		// when
		when(importRepo.findImportMaterialDetailByImportMaterialId(Mockito.anyLong()))
				.thenReturn(importMaterialDetailExpect);

		// actual
		ImportMaterialDetailDto ImportMaterialDetailActual = importService
				.getImportMaterialDetailByImportMaterialId(3L);

		assertThat(ImportMaterialDetailActual.getImportCode()).isEqualTo(importMaterialDetailExpect.getImportCode());
		assertThat(ImportMaterialDetailActual.getCreatedDate()).isEqualTo(importMaterialDetailExpect.getCreatedDate());
		assertThat(ImportMaterialDetailActual.getSupplierName())
				.isEqualTo(importMaterialDetailExpect.getSupplierName());
		assertThat(ImportMaterialDetailActual.getWarehouseName())
				.isEqualTo(importMaterialDetailExpect.getWarehouseName());
		assertThat(ImportMaterialDetailActual.getMaterialName())
				.isEqualTo(importMaterialDetailExpect.getMaterialName());
		assertThat(ImportMaterialDetailActual.getUnit()).isEqualTo(importMaterialDetailExpect.getUnit());
		assertThat(ImportMaterialDetailActual.getQuantity()).isEqualTo(importMaterialDetailExpect.getQuantity());
		assertThat(ImportMaterialDetailActual.getUnitPrice()).isEqualTo(importMaterialDetailExpect.getUnitPrice());
		assertThat(ImportMaterialDetailActual.getTotalAmount()).isEqualTo(importMaterialDetailExpect.getTotalAmount());
		assertThat(ImportMaterialDetailActual.getExpireDate()).isEqualTo(importMaterialDetailExpect.getExpireDate());
	}

}
