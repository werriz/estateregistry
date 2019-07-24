package eu.werriz.estateregistry.api;

import eu.werriz.estateregistry.service.CalculateTaxesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping("/calculate/taxes")
public class CalculateTaxesController
{

	private static final Logger logger = LoggerFactory.getLogger(CalculateTaxesController.class);

	private final CalculateTaxesService service;

	@Autowired
	public CalculateTaxesController(final CalculateTaxesService service) {
		this.service = service;
	}

	@GetMapping("/{ownerId}")
	public ResponseEntity<BigDecimal> calculateTaxes(@PathVariable final Long ownerId)
	{
		logger.trace("entry calculateTaxes, ownerId='{}'", ownerId);
		final BigDecimal calculatedTaxes = service.calculateTaxesForOwner(ownerId);

		return ResponseEntity.ok(calculatedTaxes);
	}
}
