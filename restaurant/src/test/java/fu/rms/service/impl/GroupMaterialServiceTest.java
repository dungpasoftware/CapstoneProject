package fu.rms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import fu.rms.AbstractSpringBootTest;
import fu.rms.dto.GroupMaterialDto;
import fu.rms.entity.GroupMaterial;
import fu.rms.repository.GroupMaterialRepository;

public class GroupMaterialServiceTest extends AbstractSpringBootTest {

	@Autowired
	private GroupMaterialService groupMaterialService;
	
	@MockBean
	private GroupMaterialRepository groupMaterialRepo;
	
	private List<GroupMaterial> groupMaterials;
	
	@BeforeEach
	public void setUp() {
		
		GroupMaterial groupMaterial1 = new GroupMaterial(); // groupMaterial 1
		groupMaterial1.setGroupId(1L);
		groupMaterial1.setGroupName("Đồ Lạnh");
		
		GroupMaterial groupMaterial2 = new GroupMaterial(); // groupMaterial 2
		groupMaterial2.setGroupId(2L);
		groupMaterial2.setGroupName("Đồ Khô");
		
		GroupMaterial groupMaterial3 = new GroupMaterial(); // groupMaterial 3
		groupMaterial3.setGroupId(3L);
		groupMaterial3.setGroupName("Đồ Gia vị");
		
		groupMaterials = new ArrayList<>();
		groupMaterials.add(groupMaterial1);
		groupMaterials.add(groupMaterial2);
		groupMaterials.add(groupMaterial3);	
	}
	
	@Test
	@DisplayName("Get All GroupMaterial")
	public void testWhenGetAll() {
		// expect
		List<GroupMaterial> groupMaterialsExpect = groupMaterials;

		// when
		when(groupMaterialRepo.findAll()).thenReturn(groupMaterialsExpect);

		// actual
		List<GroupMaterialDto> groupMaterialsActual = groupMaterialService.getAll();

		// test
		assertThat(groupMaterialsActual.get(0).getGroupId()).isEqualTo(groupMaterialsExpect.get(0).getGroupId());
		assertThat(groupMaterialsActual.get(0).getGroupName()).isEqualTo(groupMaterialsExpect.get(0).getGroupName());
		assertThat(groupMaterialsActual.size()).isEqualTo(groupMaterialsExpect.size());

	}
	
	@Test
	@DisplayName("Get All GroupMaterial Empty")
	public void testWhenGetAllEmpty() {
		// expect
		List<GroupMaterial> groupMaterialsExpect = new ArrayList<>();

		// when
		when(groupMaterialRepo.findAll()).thenReturn(groupMaterialsExpect);

		// actual
		List<GroupMaterialDto> groupMaterialsActual = groupMaterialService.getAll();

		// test
		assertThat(groupMaterialsActual).isNull();

	}
}
