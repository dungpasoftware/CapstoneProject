package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fu.rms.constant.MessageErrorConsant;
import fu.rms.constant.StatusConstant;
import fu.rms.dto.ImportAndExportDto;
import fu.rms.dto.ImportMaterialDetailDto;
import fu.rms.dto.MaterialDto;
import fu.rms.entity.Dish;
import fu.rms.entity.GroupMaterial;
import fu.rms.entity.Material;
import fu.rms.entity.Option;
import fu.rms.entity.Quantifier;
import fu.rms.entity.QuantifierOption;
import fu.rms.entity.Status;
import fu.rms.exception.DeleteException;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;
import fu.rms.mapper.MaterialMapper;
import fu.rms.repository.DishRepository;
import fu.rms.repository.GroupMaterialRepository;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.request.MaterialRequest;
import fu.rms.respone.SearchRespone;
import fu.rms.service.IMaterialService;
import fu.rms.utils.Utils;

@Service
public class MaterialService implements IMaterialService {

	@Autowired
	private MaterialRepository materialRepo;
	
	@Autowired
	private GroupMaterialRepository GroupMaterialRepo;
	
	@Autowired
	private StatusRepository statusRepo;
	
	@Autowired
	private DishRepository dishRepo;
	
	@Autowired
	private OptionRepository optionRepo;
	
	@Autowired
	private MaterialMapper materialMapper;

	@Override
	public List<MaterialDto> getAll() {
		List<Material> materials = materialRepo.findByStatusId(StatusConstant.STATUS_MATERIAL_AVAILABLE);
		List<MaterialDto> materialDtos = materials.stream().map(materialMapper::entityToDto)
				.collect(Collectors.toList());
		return materialDtos;
	}

	@Override
	public MaterialDto getById(Long id) {
		Material material = materialRepo.findById(id)
				.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_MATERIAL));
		return materialMapper.entityToDto(material);
	}

	@Transactional
	@Override
	public MaterialDto update(MaterialRequest materialRequest, Long id) {

		Material saveMaterial = materialRepo.findById(id).map(material -> {
			//set basic information for material
			//check material code
			
//			String materialCode=materialRequest.getMaterialCode();
//			while(true) {
//				if(materialRepo.findByMaterialCode(materialCode)!=null) {
//					if(materialCode.equals(material.getMaterialCode())) {
//						break;
//					}
//					materialCode=Utils.generateDuplicateCode(materialCode);
//				}else {
//					break;
//				}
//			}
//			
//			material.setMaterialCode(materialCode);
			material.setMaterialName(materialRequest.getMaterialName());
			material.setUnitPrice(materialRequest.getUnitPrice());
			material.setTotalPrice(Utils.multiBigDecimalToDouble(material.getRemain(), materialRequest.getUnitPrice()));			// cập nhật lại tổng giá
			material.setRemainNotification(materialRequest.getRemainNotification());
			//set Group Material
			if(materialRequest.getGroupMaterialId()!=null) {
				Long groupMaterialId=materialRequest.getGroupMaterialId();
				GroupMaterial groupMaterial=GroupMaterialRepo.findById(groupMaterialId)
						.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_GROUP_MATERIAL));
				material.setGroupMaterial(groupMaterial);
			}else {
				material.setGroupMaterial(null);
			}
			List<Dish> dishes = dishRepo.findByMaterialId(material.getMaterialId());
			for (Dish dish : dishes) {
				Double dishCost = 0D;
				if(dish.getQuantifiers() != null) {
					for (Quantifier quantifier : dish.getQuantifiers()) {
						if (quantifier.getMaterial().getMaterialId() == material.getMaterialId()) {
							Double cost = Math.ceil(material.getUnitPrice() * quantifier.getQuantity());
							quantifier.setCost(cost);
							quantifier.setUnit(material.getUnit());
							dishCost += cost;
						} else {
							dishCost += quantifier.getCost();
						}
					}
					dishCost = Utils.roundUpDecimal(dishCost);
					Double different = Utils.subtractBigDecimalToDouble(dish.getCost(), dishCost);
					dish.setCost(dishCost);
					dish.setDishCost(Utils.subtractBigDecimalToDouble(dish.getDishCost(), different));	// sửa giá thành
					Dish newDish = dishRepo.save(dish);
					if(newDish==null) {
						throw new UpdateException(MessageErrorConsant.ERROR_UPDATE_DISH);
					}
				}
			}
			
			List<Option> options=optionRepo.findByMaterialId(material.getMaterialId());
			for (Option option : options) {
				Double optionCost = 0D;
				if(option.getQuantifierOptions() != null) {
					for (QuantifierOption quantifierOption : option.getQuantifierOptions()) {
						if (quantifierOption.getMaterial().getMaterialId() == material.getMaterialId()) {
							Double cost = Math.ceil(material.getUnitPrice() * quantifierOption.getQuantity());
							quantifierOption.setCost(cost);
							quantifierOption.setUnit(material.getUnit());
							optionCost += cost;
						} else {
							optionCost += quantifierOption.getCost();
						}
					}
					optionCost = Utils.roundUpDecimal(optionCost);
					Double different = Utils.subtractBigDecimalToDouble(option.getCost(), optionCost);
					option.setCost(optionCost);
					option.setOptionCost(Utils.subtractBigDecimalToDouble(option.getOptionCost(), different));
					Option newOption = optionRepo.save(option);
					if(newOption==null) {
						throw new UpdateException(MessageErrorConsant.ERROR_UPDATE_OPTION);
					}
				}
			}
			
			return material;
			
		}).orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_MATERIAL));
		
		saveMaterial=materialRepo.save(saveMaterial);
		
		if(saveMaterial==null) {
			throw new UpdateException(MessageErrorConsant.ERROR_UPDATE_MATERIAL);
		}
		return materialMapper.entityToDto(saveMaterial);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		
		Material saveMaterial=materialRepo.findById(id).map(material ->{	
			Status status=statusRepo.findById(StatusConstant.STATUS_MATERIAL_EXPIRE)
					.orElseThrow(()-> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_STATUS));
			material.setStatus(status);
			return material;
		})
		.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_MATERIAL));
		
		saveMaterial=materialRepo.save(saveMaterial);
		
		if(saveMaterial==null) {
			throw new DeleteException(MessageErrorConsant.ERROR_DELETE_MATERIAL);
		}	

	}

	@Override
	public SearchRespone<MaterialDto> search(String materialCode, Long groupId, Integer page) {
		
		//check page
		if (page==null || page<=0) {//check page is null or = 0 => set = 1
			page=1;
		}
		//Pageable with 5 item for every page
		Pageable pageable=PageRequest.of(page-1, 5);
		
		
		//search
		Page<Material> pageMaterial = materialRepo.search(materialCode, groupId, StatusConstant.STATUS_MATERIAL_AVAILABLE, pageable);
		
		//create new searchRespone
		SearchRespone<MaterialDto> searchRespone=new SearchRespone<MaterialDto>();
		//set current page
		searchRespone.setPage(page);
		//set total page
		searchRespone.setTotalPages(pageMaterial.getTotalPages());
		//set list result material
		List<Material> materials = pageMaterial.getContent();	
		List<MaterialDto> materialDtos=materials.stream().map(materialMapper::entityToDto).collect(Collectors.toList());
		searchRespone.setResult(materialDtos);
		
		return searchRespone;
	}

	@Override
	public List<ImportAndExportDto> getImportAndExportById(Long id) {
		return materialRepo.findImportAndExportById(id);
	}

	@Override
	public ImportMaterialDetailDto getImportMaterialDetailByImportMaterialId(Long importMaterialId) {
		return materialRepo.findImportMaterialDetailByImportMaterialId(importMaterialId);
	}

}
