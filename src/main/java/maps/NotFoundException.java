package maps;

class NotFoundException extends RuntimeException {

	NotFoundException(Long id) {
		super("Could not find id " + id);
	}
}
