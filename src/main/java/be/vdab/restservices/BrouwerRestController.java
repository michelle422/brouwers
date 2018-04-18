package be.vdab.restservices;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.vdab.entities.Brouwer;
import be.vdab.exceptions.BrouwerNietGevondenException;
import be.vdab.services.BrouwerService;

@RestController
@RequestMapping("/brouwers")
@ExposesResourceFor(Brouwer.class)
class BrouwerRestController {
	private BrouwerService brouwerService;
	private final EntityLinks entityLinks;

	BrouwerRestController(BrouwerService brouwerService, EntityLinks entityLinks) {
		this.brouwerService = brouwerService;
		this.entityLinks = entityLinks;
	}
	
	@GetMapping("{brouwer}")
	BrouwerResource read(@PathVariable Brouwer brouwer) {
		if (brouwer == null) {
			throw new BrouwerNietGevondenException();
		}
		return new BrouwerResource(brouwer, entityLinks);
	}
	
	@ExceptionHandler(BrouwerNietGevondenException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	void brouwerNietGevonden() {}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	HttpHeaders create(@RequestBody @Valid Brouwer brouwer) {
		brouwerService.create(brouwer);
		HttpHeaders headers = new HttpHeaders();
		Link link = entityLinks.linkToSingleResource(Brouwer.class, brouwer.getId());
		headers.setLocation(URI.create(link.getHref()));
		return headers;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String brouwerMetVerkeerdeProperties(MethodArgumentNotValidException ex) {
		StringBuilder fouten = new StringBuilder();
		ex.getBindingResult().getFieldErrors()
			.forEach(error -> fouten.append(error.getField()).append(":")
					.append(error.getDefaultMessage()).append('\n'));
		fouten.deleteCharAt(fouten.length() - 1);
		return fouten.toString();
	}
	
	@GetMapping(params = "beginnaam")
	BrouwersResource findAll(String beginnaam) {
		return new BrouwersResource(brouwerService.findByNaam(beginnaam), entityLinks);
	}
}
