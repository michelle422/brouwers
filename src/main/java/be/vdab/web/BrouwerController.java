package be.vdab.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.services.BrouwerService;

@Controller
@RequestMapping("/brouwers")
class BrouwerController {
	private static final String BROUWERS_VIEW = "brouwers/brouwers";
	private static final String TOEVOEGEN_VIEW = "brouwers/toevoegen";
	private static final String BEGINNAAM_VIEW = "brouwers/beginnaam";
	private static final String ALFABET_VIEW = "brouwers/opalfabet";
	private final char[] alfabet = new char['Z' - 'A' + 1];
	private final BrouwerService brouwerService;
	
	BrouwerController(BrouwerService brouwerService) {
		this.brouwerService = brouwerService;
		for (char letter = 'A'; letter <= 'Z'; letter++) {
			alfabet[letter - 'A'] = letter;
		}
	}
	
	@GetMapping("toevoegen")
	String toevoegen() {
		return TOEVOEGEN_VIEW;
	}
	
	@GetMapping("beginnaam") 
	ModelAndView beginNaamForm() {
		return new ModelAndView(BEGINNAAM_VIEW).addObject(new BeginNaamForm());
	}
	
	@GetMapping(params = "beginnaam")
	ModelAndView beginNaam(@Valid BeginNaamForm form, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView(BEGINNAAM_VIEW);
		if (!bindingResult.hasErrors()) {
			modelAndView.addObject("brouwers", brouwerService.findByNaam(form.getBeginnaam()));
		}
		return modelAndView;
	}
	
	@GetMapping
	ModelAndView brouwers() {
		return new ModelAndView(BROUWERS_VIEW, "brouwers", brouwerService.findAll());
	}
	
	@GetMapping("opalfabet")
	ModelAndView opAlfabetForm() {
		return new ModelAndView(ALFABET_VIEW, "alfabet", alfabet);
	}
		
	@GetMapping(params="letter")
	ModelAndView opalfabet(@RequestParam char letter) {
		return new ModelAndView(ALFABET_VIEW)
				.addObject("alfabet", alfabet)
				.addObject("brouwers", brouwerService.findByNaam(String.valueOf(letter)));
	}
}
