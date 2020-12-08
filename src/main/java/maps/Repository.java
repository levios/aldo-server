package maps;

import maps.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

interface Repository extends JpaRepository<Person, Long> {

}
