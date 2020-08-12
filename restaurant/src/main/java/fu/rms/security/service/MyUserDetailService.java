package fu.rms.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fu.rms.entity.Staff;
import fu.rms.repository.StaffRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private StaffRepository staffRepo;

	@Override
	public MyUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
		Staff staff=staffRepo.findByPhone(username)
				.orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy số điện thoại: "+username));
		return MyUserDetail.build(staff);
	}
	
	
}
