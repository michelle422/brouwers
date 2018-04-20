package be.vdab.restclients;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import be.vdab.exceptions.KanTemperatuurNietLezenException;

@Component
class OpenWeatherMapClient implements WeerClient {
	private static final Logger LOGGER = Logger.getLogger(OpenWeatherMapClient.class.getName());
	private final String openWeatherURL;
	private final RestTemplate restTemplate;
	
	OpenWeatherMapClient(@Value("${openWeatherMapURL}") String openWeatherURL, 
			RestTemplate restTemplate) {
		this.openWeatherURL = openWeatherURL;
		this.restTemplate = restTemplate;
	}

	@Override
	public BigDecimal getTemperatuur(String plaats) {
		try {
			return restTemplate.getForObject(openWeatherURL, Current.class, plaats)
					.temperature.value;
		} catch (RestClientException ex) {
			LOGGER.log(Level.SEVERE, "kan temperatuur niet lezen", ex);
			throw new KanTemperatuurNietLezenException();
		}
	}
}
