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

}
