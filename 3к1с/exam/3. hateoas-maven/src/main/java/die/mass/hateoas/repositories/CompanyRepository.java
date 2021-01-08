package die.mass.hateoas.repositories;

import die.mass.entities.adapters.Socket;
import die.mass.entities.general.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
