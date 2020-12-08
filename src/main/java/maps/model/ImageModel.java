package maps.model;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;


@Entity
@Table(name = "image")
public class ImageModel {

	public ImageModel() {
		super();
	}

	public ImageModel(Long personId, String name, String type, byte[] picByte) {
		this.personId = personId;
		this.picByte = picByte;
		this.name = name;
		this.type = type;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue
//	@GeneratedValue(generator="sqlite")
//	@TableGenerator(name="sqlite", table="sqlite_sequence",
//			pkColumnName="name", valueColumnName="seq",
//			pkColumnValue="image")
	public Long id;

	@Column(name = "person_id")
	public Long personId;

    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
	@Column(name = "picByte", length = 1000)
	public byte[] picByte;

	@Column(name = "name")
	public String name;

	@Column(name = "type")
	public String type;


	public byte[] getPicByte() {
		return picByte;
	}
	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}
}