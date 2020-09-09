package fu.rms.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fu.rms.dto.GroupMaterialDto;
import fu.rms.service.IGroupMaterialService;

public class GroupMaterialControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private IGroupMaterialService groupMaterialService;
	
	@InjectMocks
	private GroupMaterialController groupMaterialController;
	
	private List<GroupMaterialDto> groupMaterials;
	
	@BeforeEach
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(groupMaterialController).build();

		GroupMaterialDto groupMaterial1 = new GroupMaterialDto();
		groupMaterial1.setGroupId(1L);
		groupMaterial1.setGroupName("Đồ ăn sáng");
		
		GroupMaterialDto groupMaterial2 = new GroupMaterialDto();
		groupMaterial2.setGroupId(2L);
		groupMaterial2.setGroupName("Đồ ăn trưa");
		
		groupMaterials = new ArrayList<>();
		groupMaterials.add(groupMaterial1);
		groupMaterials.add(groupMaterial2);
		
	}
	
	@Test
	@DisplayName("Get All Group Material")
	public void testWhenGetAll() throws Exception {

		// expect
		List<GroupMaterialDto> groupMaterialsExpect = groupMaterials;

		// when
		when(groupMaterialService.getAll()).thenReturn(groupMaterialsExpect);

		// test
		mockMvc.perform(get("/group-material/all")).andExpect(status().isOk());

		verify(groupMaterialService, times(1)).getAll();

	}
}
