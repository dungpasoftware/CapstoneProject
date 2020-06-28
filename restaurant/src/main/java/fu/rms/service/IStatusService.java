package fu.rms.service;

import fu.rms.entity.Status;

public interface IStatusService {

	Status findByStatusNameAndStatusCode(String statusName,int statusCode);
}
