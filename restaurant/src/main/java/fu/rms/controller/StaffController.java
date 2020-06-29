package fu.rms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.entity.Staff;
import fu.rms.security.JWTService;
import fu.rms.service.impl.StaffService;

@RestController
public class StaffController {

	@Autowired
	StaffService staffService;
	
	@Autowired
	JWTService jwtService;
	
	@GetMapping("/get_staff")
	public List<Staff> getAllStaffs(){
		List<Staff> getAllStaff = staffService.findAllStaff();
		return getAllStaff;
	}
	
	@PostMapping("/sign_in")
	public String signIn(@RequestParam(value="phone") String phone, @RequestParam(value="password") String password){
		String result = "";
		HttpStatus httpStatus = null;
		try {
			if(staffService.checkPhoneAndPassword(phone, password)) {
				result = jwtService.generateTokenLogin(phone);
				httpStatus = HttpStatus.OK;
			}else {
				result = "Wrong phone and password";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
			
		} catch (Exception e) {
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
//		return new ResponseEntity<String>(result, httpStatus);
		return result;
		
	}
	
	
}
