package com.uc_mobileapps.examples.rest;


import com.uc_mobileapps.examples.entities.Customer;
import com.uc_mobileapps.examples.entities.Trip;
import com.uc_mobileapps.examples.filters.TripFilterVO;
import com.uc_mobileapps.examples.rest.dao.CustomerRepository;
import com.uc_mobileapps.examples.rest.dao.TripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * Example service class containing business logic used by the REST controllers.
 */
@Service
public class EntityService {

    private static final Logger logger = LoggerFactory.getLogger(EntityService.class);

    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;

    @Autowired
    public EntityService(CustomerRepository customerRepository, TripRepository tripRepository) {
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
    }

    @Transactional(readOnly = true)
    public long countTrips() {
        return tripRepository.count();
    }

    @Transactional
    public Trip saveTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Transactional
    public Trip updateTrip(Trip trip) {
        Trip existing = null;
        if (trip.getId() != null) {
            existing = tripRepository.findById(trip.getId()).orElse(null);
        }
        if (existing != null) {
        }
        return tripRepository.save(trip);
    }

    @Transactional(readOnly = true)
    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<Trip> findTrips(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Trip> findFiltered(TripFilterVO filter, Pageable pageable) {
        logger.info("findFiltered: {} {}", pageable, filter);
        Page<Trip> items = tripRepository.findFilteredBy(filter, pageable);
        return items;
    }

    @Transactional(readOnly = true)
    public long countFiltered(TripFilterVO filter) {
        return tripRepository.countFilteredBy(filter);
    }

    @Transactional
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countCustomers() {
        return customerRepository.count();
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Customer customer) {
        Customer existing = null;
        if (customer.getId() != null) {
            existing = customerRepository.findById(customer.getId()).orElse(null);
        }
        if (existing != null) {
        }
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<Customer> findCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }    
    
}
