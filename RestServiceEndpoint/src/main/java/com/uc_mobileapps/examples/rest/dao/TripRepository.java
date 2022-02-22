package com.uc_mobileapps.examples.rest.dao;

import com.uc_mobileapps.examples.entities.Trip;
import com.uc_mobileapps.examples.filters.TripFilterVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Trip data.
 */
public interface TripRepository extends CrudRepository<Trip, Long> {

    Trip findById(long id);

    Page<Trip> findAll(Pageable pageable);

    long count();

    /**
     * These filter queries do not support null-values in the filter value objects.
     * @param filter filter criteria
     * @param pageable paging parameters
     * @return item page
     */
    @Query("SELECT t FROM Trip t WHERE " +
            " (LENGTH(:#{#f.source})=0 or t.source LIKE %:#{#f.source}%)" +
            " and (LENGTH(:#{#f.destination})=0 or t.destination LIKE %:#{#f.destination}%)" +
            " and (t.address is null or " +
            " ((LENGTH(:#{#f.address.street})=0 or t.address.street LIKE %:#{#f.address.street}%)" +
            "  and (LENGTH(:#{#f.address.houseNumber})=0 or t.address.houseNumber LIKE %:#{#f.address.houseNumber}%)))")
    Page<Trip> findFilteredBy(@Param("f") TripFilterVO filter, Pageable pageable);

    /**
     * Counts items matching the filter.
     * @param filter filter criteria
     * @return number of matching items
     */
    @Query("SELECT count(t) FROM Trip t WHERE" +
            " (LENGTH(:#{#f.source})=0 or t.source LIKE %:#{#f.source}%)" +
            " and (LENGTH(:#{#f.destination})=0 or t.destination LIKE %:#{#f.destination}%)" +
            " and (t.address is null or " +
            " ((LENGTH(:#{#f.address.street})=0 or t.address.street LIKE %:#{#f.address.street}%)" +
            "  and (LENGTH(:#{#f.address.houseNumber})=0 or t.address.houseNumber LIKE %:#{#f.address.houseNumber}%)))")
    long countFilteredBy(@Param("f") TripFilterVO filter);

}