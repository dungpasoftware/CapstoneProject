package fu.rms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.entity.Status;
import fu.rms.repository.StatusRepository;
import fu.rms.service.IStatusService;

@Service
public class StatusService implements IStatusService{

	
	@Autowired
	private StatusRepository statusRepo;
	@Override
	public Status findByStatusNameAndStatusCode(String statusName, int statusCode) {
		return statusRepo.findByStatusNameAndStatusCode(statusName, statusCode);
	}

}
