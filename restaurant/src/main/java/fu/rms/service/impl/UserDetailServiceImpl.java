package fu.rms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fu.rms.entity.Staff;
import fu.rms.repository.StaffRepository;
import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserDetailServiceImpl implements UserDetailsService{

	private StaffRepository staffRepo;
	@Autowired
	RoleService roleService;

	public UserDetailServiceImpl(StaffRepository staffRepo) {
		this.staffRepo = staffRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
		Staff staff = staffRepo.findByPhone(phone);
		if(staff == null) {
			throw new UsernameNotFoundException(phone);
		}
		return new User(staff.getPhone(), staff.getPassword(), getAuthorities());
	}
	
	private List<GrantedAuthority> getAuthorities(){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (int i = 0; i < roleService.findAllRoles().size(); i++) {
			authorities.add(new SimpleGrantedAuthority(roleService.findAllRoles().get(i).getRoleName()));
		}
		return authorities;
	}
	
}
