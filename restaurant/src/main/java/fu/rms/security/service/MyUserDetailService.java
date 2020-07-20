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
	private StaffRepository staffRepository;

	@Override
	public MyUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
		Staff staff=staffRepository.findByPhone(username)
				.orElseThrow(()-> new UsernameNotFoundException("User not found with phone: "+username));
		return MyUserDetail.build(staff);
	}
	
	
}
