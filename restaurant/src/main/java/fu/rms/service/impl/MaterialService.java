package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.MaterialDto;
import fu.rms.entity.GroupMaterial;
import fu.rms.entity.Material;
import fu.rms.entity.Status;
import fu.rms.exception.DeleteException;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;
import fu.rms.mapper.MaterialMapper;
import fu.rms.repository.GroupMaterialRepository;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.request.MaterialRequest;
import fu.rms.request.SearchMaterialRequest;
import fu.rms.respone.SearchRespone;
import fu.rms.service.IMaterialService;

@Service
public class MaterialService implements IMaterialService {

	@Autowired
	private MaterialRepository materialRepo;
	
	@Autowired
	private GroupMaterialRepository GroupMaterialRepo;
	
	@Autowired
	private StatusRepository statusRepo;
	
	@Autowired
	private MaterialMapper materialMapper;

	@Override
	public List<MaterialDto> getAll() {
		Status status=statusRepo.findById(StatusConstant.STATUS_MATERIAL_AVAILABLE)
				.orElseThrow(()-> new NotFoundException("Not found Status: "+StatusConstant.STATUS_MATERIAL_AVAILABLE));
		List<Material> materials = materialRepo.findByStatus(status);
		List<MaterialDto> materialDtos = materials.stream().map(materialMapper::entityToDto)
				.collect(Collectors.toList());
		return materialDtos;
	}

	@Override
	public MaterialDto getById(Long id) {
		Material material = materialRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Not found Material: " + id));
		return materialMapper.entityToDto(material);
	}

	@Transactional
	@Override
	public MaterialDto update(MaterialRequest materialRequest, Long id) {

		Material saveMaterial = materialRepo.findById(id).map(material -> {
			//set basic information for material
			material.setMaterialName(materialRequest.getMaterialName());
			material.setUnit(materialRequest.getUnit());
			material.setUnitPrice(materialRequest.getUnitPrice());
			material.setRemainNotification(materialRequest.getRemainNotification());
			//set Group Material
			if(materialRequest.getGroupMaterialId()!=null) {
				Long groupMaterialId=materialRequest.getGroupMaterialId();
				GroupMaterial groupMaterial=GroupMaterialRepo.findById(groupMaterialId)
						.orElseThrow(() -> new NotFoundException("Not found Group Material: "+groupMaterialId));
				material.setGroupMaterial(groupMaterial);
			}else {
				material.setGroupMaterial(null);
			}
			
			return material;
			
		}).orElseThrow(() -> new NotFoundException("Not found Material: " + id));
		
		saveMaterial=materialRepo.save(saveMaterial);
		
		if(saveMaterial==null) {
			throw new UpdateException("Can't update material");
		}
		return materialMapper.entityToDto(saveMaterial);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		
		Material saveMaterial=materialRepo.findById(id).map(material ->{	
			Status status=statusRepo.findById(StatusConstant.STATUS_MATERIAL_EXPIRE)
					.orElseThrow(()-> new NotFoundException("Not found Status: "+StatusConstant.STATUS_MATERIAL_EXPIRE));
			material.setStatus(status);
			return material;
		})
		.orElseThrow(() -> new NotFoundException("Not found Material: "+id));
		
		saveMaterial=materialRepo.save(saveMaterial);
		
		if(saveMaterial==null) {
			throw new DeleteException("Can't delete Material");
		}	

	}

	@Override
	public SearchRespone<MaterialDto> search(SearchMaterialRequest searchMaterialRequest) {
		if(searchMaterialRequest.getPage()==null || searchMaterialRequest.getPage()==0) {
			searchMaterialRequest.setPage(1);
		}
		Pageable pageable=PageRequest.of(searchMaterialRequest.getPage()-1, 5);
		
		Page<Material> page = materialRepo.search(searchMaterialRequest.getMaterialCode(),searchMaterialRequest.getGroupId(),StatusConstant.STATUS_MATERIAL_AVAILABLE,pageable);
		//create new searchRespone
		SearchRespone<MaterialDto> searchRespone=new SearchRespone<MaterialDto>();
		//set current page
		searchRespone.setPage(searchMaterialRequest.getPage());
		//set total page
		searchRespone.setTotalPages(page.getTotalPages());
		//set list result material
		List<Material> materials = page.getContent();	
		List<MaterialDto> materialDtos=materials.stream().map(materialMapper::entityToDto).collect(Collectors.toList());
		searchRespone.setResult(materialDtos);
		
		return searchRespone;
	}

}
