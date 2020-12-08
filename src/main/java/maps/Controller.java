package maps;

import maps.model.ImageModel;
import maps.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@CrossOrigin
public class Controller {
	final static Logger logger = LoggerFactory.getLogger(Controller.class);

	@Autowired
	Repository repository;

	@Autowired
	ImageRepository imageRepository;

	@GetMapping("/employees")
	List<Person> all() {

		//List<EntityModel<Employee>>
		List<Person> people = repository.findAll();

		return people; // , linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}

	@PostMapping("/employees")
	Person newEmployee(@RequestBody Person newPerson) {
		return repository.save(newPerson);
	}


	@GetMapping("/employees/{id}")
	Person one(@PathVariable Long id) {

		Person person = repository.findById(id) //
				.orElseThrow(() -> new NotFoundException(id));

		return person;
	}

	@PutMapping("/employees/{id}")
	Person replaceEmployee(@RequestBody Person newPerson, @PathVariable Long id) {

		return repository.findById(id) //
				.map(person -> {
					person.becsultEletkor = (newPerson.becsultEletkor);
					person.eletkor = (newPerson.eletkor);
					person.eltunesIdeje = (newPerson.eltunesIdeje);
					person.halalBecsultIdeje = (newPerson.halalBecsultIdeje);
					person.jelzes = (newPerson.jelzes);
					person.nem = (newPerson.nem);
					person.ugyszam = (newPerson.ugyszam);


					return repository.save(person);
				}) //
				.orElseGet(() -> {
					newPerson.id = (id);
					return repository.save(newPerson);
				});
	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}

	//-----------------------------------------------
	// IMAGE
	//-----------------------------------------------

//	@GetMapping("/images/{id}")
//	List<ImageModel> images(@PathVariable Long id) {
//		return imageRepository.findByPersonId(id);
//	}

	@DeleteMapping("/images/{id}")
	Integer deleteImage(@PathVariable Long id) {
		//imageRepository.deleteById(id);
		return imageRepository.deleteByPersonId(id);
	}

//	@PostMapping("/images")
//	ImageModel newEmployee(@RequestBody ImageModel image) {
//		return imageRepository.save(image);
//	}

	@PostMapping("/images/{personid}")
	public ResponseEntity.BodyBuilder uploadImage(@PathVariable Long personid, @RequestParam("imageFile") MultipartFile file) throws IOException {
		logger.info("Original Image Byte Size - " + file.getBytes().length);
		ImageModel img = new ImageModel(
				personid,
				file.getOriginalFilename(),
				file.getContentType(),
				compressBytes(file.getBytes()));
		imageRepository.save(img);
		return ResponseEntity.status(HttpStatus.OK);
	}

	@GetMapping("/images/{id}")
	public List<ImageModel> getImages(@PathVariable Long id) throws IOException {
		final List<ImageModel> retrievedImage = imageRepository.findByPersonId(id);
//		retrievedImage.orElseThrow(() -> new RuntimeException("Image not found"));
		Optional<ImageModel> first = retrievedImage.stream().findFirst();
		if (first.isPresent()) {
			List<ImageModel> newList = new ArrayList<>();
			ImageModel img = first.get();
			ImageModel i = new ImageModel(
					img.id,
					img.name,
					img.type,
					decompressBytes(img.getPicByte()));
			newList.add(i);
			return newList;
		} else {
			return new ArrayList<>();
		}
	}

	@GetMapping("/imagecount/{id}")
	public Integer getImageCount(@PathVariable Long id) throws IOException {
		return imageRepository.findByPersonId(id).size();
	}

	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			logger.info("Compressed Image Byte Size - " + outputStream.toByteArray().length);
			return outputStream.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("Problem decompressing image");
		}
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		byte[] buffer = new byte[1024];
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			return outputStream.toByteArray();
		} catch (Exception e) {
			logger.error("Problem decompressing image", e);
			throw new RuntimeException("Problem decompressing image");
		}
	}
}
