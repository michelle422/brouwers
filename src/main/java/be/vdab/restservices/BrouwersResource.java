package be.vdab.restservices;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

import be.vdab.entities.Brouwer;

@XmlRootElement 
@XmlAccessorType(XmlAccessType.FIELD) 
@JsonAutoDetect(fieldVisibility = Visibility.ANY) 
public class BrouwersResource extends ResourceSupport {
	@XmlElement(name = "brouwer")
	@JsonProperty("brouwers")
	private final List<BrouwerIdNaam> brouwersIdNaam = new ArrayList<>();
	
	BrouwersResource() {
	}
	
	BrouwersResource(Iterable<Brouwer> brouwers, EntityLinks entityLinks) {
		for (Brouwer brouwer : brouwers) {
			this.brouwersIdNaam.add(new BrouwerIdNaam(brouwer));
			this.add(entityLinks.linkToSingleResource(Brouwer.class, brouwer.getId())
					.withRel("detail"));
		}
		this.add(entityLinks.linkToCollectionResource(Brouwer.class));
	}
}
