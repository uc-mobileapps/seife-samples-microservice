package com.uc_mobileapps.examples;

import com.uc_mobileapps.examples.entities.Address;
import com.uc_mobileapps.examples.entities.Customer;
import com.uc_mobileapps.examples.entities.Trip;
import com.uc_mobileapps.examples.rest.dao.CustomerRepository;
import com.uc_mobileapps.examples.rest.dao.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Configuration
@EnableSpringDataWebSupport
public class RestServiceConfiguration {

    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;

    @Autowired
    public RestServiceConfiguration(CustomerRepository customerRepository, TripRepository tripRepository) {
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
    }

    @Bean
    ApplicationRunner initializeData() {
        return args -> {
            setup();
        };
    }

    @Value("${:classpath:cities.txt}")
    private Resource cityList;

    /**
     * Fill database with some sample values.
     */
    @Transactional
    public void setup() throws IOException {
        if (tripRepository.count() > 0) {
            return;
        }

        Random r = new Random();
        List<Customer> customers = Arrays.asList(
                new Customer("Diana", "Gabaldon"), new Customer("Suzanne", "Collins"),
                new Customer("Henning", "Mankell"), new Customer("Terry", "Pratchett"),
                new Customer("Tommy", "Jaud"));
        customers = customers.stream().map(customerRepository::save).collect(Collectors.toList());

        String[] streets = new String[]{"Short Street", "Kingsley Road", "Sycamore Drive", "Bristol Road",
                "Spring Lane", "Priory Lane", "Stanley Road", "Ivy Lane", "Hawthorn Way", "Lime Avenue"};

        customers.forEach(customer -> {
            Address address = new Address();
            int index = r.nextInt(streets.length);
            address.setStreet(streets[index]);
            address.setHouseNumber(String.valueOf(index+1));
            customer.setAddress(address);
        });
        customers = customers.stream().map(customerRepository::save).collect(Collectors.toList());

        List<String> cities = Files.readAllLines(cityList.getFile().toPath(), StandardCharsets.UTF_8);
        String city = cities.get(r.nextInt(cities.size()));
        for (int i=0;i<500;i++) {
            String nextCity = cities.get(r.nextInt(cities.size()));

            Trip trip = new Trip();
            trip.setSource(city);
            trip.setDestination(nextCity);
            trip.setDistanceKm(r.nextFloat());
            trip.setTripStart(LocalDateTime.ofEpochSecond(r.nextInt(), 0, ZoneOffset.UTC));

            Customer customer = customers.get(r.nextInt(customers.size()));
            trip.setVisitedCustomer(customer);
            trip.setAddress(customer.getAddress());

            tripRepository.save(trip);
            city = nextCity;
        }
    }
}
