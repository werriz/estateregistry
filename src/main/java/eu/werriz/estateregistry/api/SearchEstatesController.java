package eu.werriz.estateregistry.api;

import eu.werriz.estateregistry.form.EstatePropertyForm;
import eu.werriz.estateregistry.service.SearchEstatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("estates/search")
public class SearchEstatesController {

	private static final Logger logger = LoggerFactory.getLogger(SearchEstatesController.class);

	private final SearchEstatesService service;

	@Autowired
	public SearchEstatesController(final SearchEstatesService service) {
		this.service = service;
	}

	@GetMapping("/similar/{estateId}")
	public ResponseEntity<List<EstatePropertyForm>> getSimilarEstates(@PathVariable final Long estateId) {
		final List<EstatePropertyForm> similarEstates = service.searchSimilarEstates(estateId);

		return ResponseEntity.ok(similarEstates);
	}

}
