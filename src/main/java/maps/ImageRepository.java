package maps;

import maps.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {
	List<ImageModel> findByPersonId(Long personId);

//	@Query(value = "SELECT COUNT(u) FROM ImageModel u WHERE person_id = :p_id")
//	Integer count(@Param("p_id") long personId);

	@Modifying
	@Transactional
	@Query("DELETE FROM ImageModel m where m.personId=:personId")
	Integer deleteByPersonId(@Param("personId") Long personId);
}