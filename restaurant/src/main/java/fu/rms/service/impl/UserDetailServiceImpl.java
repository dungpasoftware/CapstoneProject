package fu.rms.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fu.rms.entity.Staff;
import fu.rms.repository.StaffRepository;
import static java.util.Collections.emptyList;

public class UserDetailServiceImpl implements UserDetailsService{

	private StaffRepository staffRepo;

	public UserDetailServiceImpl(StaffRepository staffRepo) {
		this.staffRepo = staffRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Staff staff = staffRepo.findByEmail(email);
		if(staff == null) {
			throw new UsernameNotFoundException(email);
		}
		return new User(staff.getEmail(), staff.getPassword(), emptyList());
	}
	
}
