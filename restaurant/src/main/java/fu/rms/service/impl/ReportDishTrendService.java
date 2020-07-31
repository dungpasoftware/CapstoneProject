package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.ReportDishTrendDto;
import fu.rms.entity.ReportDishTrend;
import fu.rms.mapper.ReportDishTrendMapper;
import fu.rms.repository.ReportDishTrendRepository;
import fu.rms.service.IReportDishTrendService;

@Service
public class ReportDishTrendService implements IReportDishTrendService{

	@Autowired
	ReportDishTrendRepository reportRepo;
	
	@Autowired
	ReportDishTrendMapper reportMapper;
	
	@Override
	public int insertReportDishTrend(ReportDishTrendDto dto) {
		
		ReportDishTrend entity = new ReportDishTrend();
		try {
			entity = reportMapper.dtoToEntity(dto);
			reportRepo.save(entity);
		} catch (Exception e) {
			throw e;
		}
	
		return 1;
	}

	@Override
	public List<ReportDishTrendDto> getAll() {
		List<ReportDishTrend> listEntity = reportRepo.findAll();
		List<ReportDishTrendDto> listDto = null;
		if(listEntity.size()!=0) {
			listDto = listEntity.stream().map(reportMapper::entityToDto).collect(Collectors.toList());
		}
		return listDto;
	}

}
