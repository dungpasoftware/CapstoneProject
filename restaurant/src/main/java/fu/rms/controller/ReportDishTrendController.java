package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.ReportDishTrendDto;
import fu.rms.service.IReportDishTrendService;

@RestController
public class ReportDishTrendController {

	@Autowired
	IReportDishTrendService reportService;
	
	@GetMapping("/report/all")
	public List<ReportDishTrendDto> getAll() {
		return reportService.getAll();
	}
}