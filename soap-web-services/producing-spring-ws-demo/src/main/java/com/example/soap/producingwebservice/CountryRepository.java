package com.example.soap.producingwebservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.example.soap.producing_web_service.Country;
import com.example.soap.producing_web_service.Currency;

import jakarta.annotation.PostConstruct;

/**
 * CountryRepository is a simple in-memory repository for storing and retrieving
 * {@link Country} objects.
 *
 * - It initializes some sample data (Spain, Poland, UK) at application startup.
 * - It allows lookup of countries by their name.
 *
 * NOTE: This is not a persistent repository — it uses a static Map to store
 * data. For production, you’d typically use a database or an external service.
 */
@Component
public class CountryRepository {

	// In-memory store for countries, keyed by country name
	private static final Map<String, Country> countries = new HashMap<>();

	/**
	 * Initializes the in-memory dataset with sample countries. This method runs
	 * automatically after the bean is constructed.
	 */
	@PostConstruct
	public void initData() {
		Country spain = new Country();
		spain.setName("Spain");
		spain.setCapital("Madrid");
		spain.setCurrency(Currency.EUR);
		spain.setPopulation(46704314);
		countries.put(spain.getName(), spain);

		Country poland = new Country();
		poland.setName("Poland");
		poland.setCapital("Warsaw");
		poland.setCurrency(Currency.PLN);
		poland.setPopulation(38186860);
		countries.put(poland.getName(), poland);

		Country uk = new Country();
		uk.setName("United Kingdom");
		uk.setCapital("London");
		uk.setCurrency(Currency.GBP);
		uk.setPopulation(63705000);
		countries.put(uk.getName(), uk);
	}

	/**
	 * Find a country by its name.
	 *
	 * @param name The name of the country (must not be null).
	 * @return The matching Country object, or null if not found.
	 */
	public Country findCountry(String name) {
		Assert.notNull(name, "The country's name must not be null");
		return countries.get(name);
	}
}
