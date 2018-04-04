package be.vdab.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import be.vdab.entities.Brouwer;
import be.vdab.valueobjects.Adres;

@Repository
class InMemoryBrouwerRepository implements BrouwerRepository {
	private final Map<Long, Brouwer> brouwers = new ConcurrentHashMap<>();
	
	InMemoryBrouwerRepository() {
		brouwers.put(1L, new Brouwer(1, "Liefmans", 1000, 
				new Adres("Marlboroughlaan", "54", 9700, "Oudenaarde")));
		brouwers.put(2L, new Brouwer(2, "Leffe", 2000, 
				new Adres("Papelostraat", "23", 9700, "Edelare")));
		brouwers.put(3L, new Brouwer(3, "Eutropius", 3000, 
				new Adres("Hogeweg", "263", 8930, "Menen")));
		brouwers.put(4L, new Brouwer(4, "Sint Bernardus", 4000, 
				new Adres("Hoogstraat", "30", 9700, "Oudenaarde")));
		brouwers.put(5L, new Brouwer(5, "Hooggaarden", 5000, 
				new Adres("Leiekaai", "25C", 9000, "Gent")));
	}
	
	@Override
	public void create(Brouwer brouwer) {
		brouwer.setId(Collections.max(brouwers.keySet())+1);
		brouwers.put(brouwer.getId(), brouwer);
	}

	@Override
	public List<Brouwer> findAll() {
		return new ArrayList<>(brouwers.values());
	}

	@Override
	public List<Brouwer> findByNaam(String beginNaam) {
		beginNaam = beginNaam.toUpperCase();
		List<Brouwer> gevondenNamen = new ArrayList<>();
		for (Brouwer brouwer : brouwers.values()) {
			if (brouwer.getNaam().startsWith(beginNaam)) {
				gevondenNamen.add(brouwer);
			}
		}
		return gevondenNamen;
	}

}
