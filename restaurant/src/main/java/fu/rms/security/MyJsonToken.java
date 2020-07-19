package fu.rms.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyJsonToken {	
	
	private String token;
	
	private Long staffId;
	
	private String staffCode;
	
	private String roleName;
}
