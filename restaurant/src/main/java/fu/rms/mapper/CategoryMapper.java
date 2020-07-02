package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.CategoryDto;
import fu.rms.entity.Category;

@Component
public class CategoryMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	public CategoryDto entityToDto(Category category) {
		CategoryDto categoryDto=modelMapper.map(category,CategoryDto.class);
		return categoryDto;
	}
	
	public Category dtoToEntity(CategoryDto categoryDto) {
		Category category=modelMapper.map(categoryDto,Category.class);
		return category;
	}
}
