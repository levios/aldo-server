package maps.model;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class Person {

	@Id
	@GeneratedValue
//	@GeneratedValue(generator="sqlite")
//	@TableGenerator(name="sqlite", table="sqlite_sequence",
//			pkColumnName="name", valueColumnName="seq",
//			pkColumnValue="employee")
	public Long id;

	@Column(name = "tipus")
	public Boolean tipus;

	@Column(name = "megtalalas_ideje")
	public String megtalalasIdeje;
	@Column(name = "becsult_eletkor")
	public String becsultEletkor;
	@Column(name = "halal_becsult_ideje")
	public String halalBecsultIdeje;

	@Column(name = "eltunes_ideje")
	public String eltunesIdeje;
	@Column(name = "eletkor")
	public String eletkor;
	@Column(name = "jelzes")
	public String jelzes;

	@Column(name = "nem")
	public String nem;
	@Column(name = "ugyszam")
	public String ugyszam;

	@Column(name = "x")
	public Double x;
	@Column(name = "y")
	public Double y;

	public Person(){}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Person person = (Person) o;
		return Objects.equals(tipus, person.tipus) &&
				Objects.equals(megtalalasIdeje, person.megtalalasIdeje) &&
				Objects.equals(becsultEletkor, person.becsultEletkor) &&
				Objects.equals(halalBecsultIdeje, person.halalBecsultIdeje) &&
				Objects.equals(eltunesIdeje, person.eltunesIdeje) &&
				Objects.equals(eletkor, person.eletkor) &&
				Objects.equals(jelzes, person.jelzes) &&
				Objects.equals(nem, person.nem) &&
				Objects.equals(ugyszam, person.ugyszam);
	}

	@Override
	public int hashCode() {
		return Objects.hash(tipus, megtalalasIdeje, becsultEletkor, halalBecsultIdeje, eltunesIdeje, eletkor, jelzes, nem, ugyszam);
	}
}
