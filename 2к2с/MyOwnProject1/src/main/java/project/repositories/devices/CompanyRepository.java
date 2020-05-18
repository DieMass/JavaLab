package project.repositories.devices;

import org.springframework.data.repository.CrudRepository;
import project.models.devices.general.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {

}
