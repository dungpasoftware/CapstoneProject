package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.MaterialDto;
import fu.rms.entity.Material;
import fu.rms.mapper.MaterialMapper;
import fu.rms.repository.MaterialRepository;
import fu.rms.service.IMaterialService;

@Service
public class MaterialService implements IMaterialService{
	
	@Autowired
	MaterialRepository materialRepo;
	
	@Autowired
	MaterialMapper materialMapper;

	@Override
	public List<MaterialDto> getListMaterial() {
		List<Material> listEntity = materialRepo.findAll();
		List<MaterialDto> listDto = null;
		if(listEntity != null && listEntity.size() != 0) {
			listDto = listEntity.stream().map(materialMapper::entityToDto).collect(Collectors.toList());
		}
		return listDto;
	}

	@Override
	public int insertMaterial(MaterialDto dto) {
		int result = 0;
		if(dto != null) {
			try {
				result = materialRepo.insertMaterial(dto.getMaterialCode(), dto.getMaterialName(), dto.getUnit(), dto.getUnitPrice(), dto.getTotalImport(), 
						dto.getTotalExport(), dto.getRemain(), dto.getRemainNotifycation(), dto.getGroupId(), dto.getStatusId());
			} catch (NullPointerException e) {
				return 0;
			}
		}
		return result;
	}

}
