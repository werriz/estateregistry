package eu.werriz.estateregistry.api;

import eu.werriz.estateregistry.form.EstatePropertyForm;
import eu.werriz.estateregistry.service.EstatePropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("estates")
public class EstatePropertyController
{

	private static final Logger logger = LoggerFactory.getLogger(EstatePropertyController.class);

	private final EstatePropertyService service;

	@Autowired
	public EstatePropertyController(final EstatePropertyService service) {
		this.service = service;
	}

	@GetMapping("/")
	public ResponseEntity<List<EstatePropertyForm>> getEstateProperties()
	{
		logger.trace("getEstateProperties() entry");
		final List<EstatePropertyForm> estates = service.getEstates();
		return ResponseEntity.ok(estates);
	}

	@PostMapping("/")
	public ResponseEntity createUpdateEstateProperties(
			 @RequestBody EstatePropertyForm form) {
		logger.trace("createUpdateEstateProperties() entry, form='{}'", form);
		Long id = service.createUpdate(form);
		logger.trace("Saved entity id: '{}'", id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteEstateProperties(@PathVariable final Long id)
	{
		logger.trace("deleteEstateProperties() entry, id='{}'", id);
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler
	public ResponseEntity<String> handleErrors(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
