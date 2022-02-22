package com.uc_mobileapps.examples.rest;

import com.uc_mobileapps.examples.entities.Trip;
import com.uc_mobileapps.examples.filters.TripFilterVO;
import com.weebmeister.seife.anno.SeifeClass;
import com.weebmeister.seife.anno.SeifeMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Controller
 */
@SeifeClass
@RestController
public class TripController {

    private static final Logger logger = LoggerFactory.getLogger(TripController.class);

    private final EntityService entityService;

    @Autowired
    public TripController(EntityService entityService) {
        this.entityService = entityService;
    }

    @SeifeMethod
    @RequestMapping("/v1/trips/page")
    public Page<Trip> listTrips(
            @PageableDefault(page = 0, size = 20)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            })
            Pageable pageable) {
        logger.info("Get trips offset {} page {}/{}", pageable.getOffset(), pageable.getOffset(), pageable.getPageSize());
        return entityService.findTrips(pageable);
    }

    @SeifeMethod
    @RequestMapping("/v1/trips/pageFiltered")
    public Page<Trip> listFilteredTrips(
            @RequestBody TripFilterVO filter,
            @PageableDefault(page = 0, size = 20)
            @SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.ASC)}) Pageable pageable) {
        logger.info("Get filtered trips offset {} page {}/{}, filter:{}",
                pageable.getOffset(), pageable.getOffset(), pageable.getPageSize(), filter);
        return entityService.findFiltered(filter, pageable);
    }

    @SeifeMethod
    @RequestMapping("/v1/trips/count")
    public long count() {
        long num = entityService.countTrips();
        logger.info("Found {} trips", num);
        return num;
    }

    @SeifeMethod
    @RequestMapping("/v1/trips/countFiltered")
    public long countFilteredBy(@RequestBody TripFilterVO filter) {
        long num = entityService.countFiltered(filter);
        logger.info("Found {} trips", num);
        return num;
    }

    /**
     * Creates a new entity or updates it if it is already present.
     * @param trip
     * @return
     */
    @SeifeMethod
    @RequestMapping(value = "/v1/trips/create", method = RequestMethod.PUT)
    public long create(@RequestBody Trip trip) {
        Trip created = entityService.saveTrip(trip);
        return created.getId();
    }

    /**
     * Patches an existing entity by updating all non-null values.
     * @param trip instance with that id must exist
     * @return
     */
    @SeifeMethod
    @RequestMapping(value = "/v1/trips/update", method = RequestMethod.PATCH)
    public long update(@RequestBody Trip trip) {
        Trip created = entityService.updateTrip(trip);
        return created.getId();
    }

    /**
     * Creates a new entity or updates it if it is already present.
     * @param id entity id to delete
     */
    @SeifeMethod
    @RequestMapping(value = "/v1/trips/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        if (id != null) {
            entityService.deleteTrip(id);
        }
    }

    @SeifeMethod
    @RequestMapping("/v1/trips/{id}")
    public Trip get(@PathVariable("id") Long id) {
        return entityService.getTripById(id);
    }

}
