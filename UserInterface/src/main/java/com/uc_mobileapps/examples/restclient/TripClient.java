package com.uc_mobileapps.examples.restclient;

import com.weebmeister.seife.anno.SeifeClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.uc_mobileapps.examples.entities.Trip;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;

import com.uc_mobileapps.examples.filters.TripFilterVO;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class is automatically maintained based on TripController in the RestClient project.
 */
@SeifeClass(classOptions = { "sourcemodel:class=com.uc_mobileapps.examples.rest.TripController," +
        "prefix=/v1/trips/,replacement=/${rest.client.apilevel}/trips/" },
        generatorOptions = {
        // generate the feign client service interface methods:
        "merge.feignclient/client.vm=com.uc_mobileapps.examples.restclient.TripClient",
})
@FeignClient(name = "tripclient", fallback = Object.class)
public interface TripClient {


	// @seife automatically generated:

 	@RequestMapping(value = "/${rest.client.apilevel}/trips/page")
	Page<Trip> listTrips(@SpringQueryMap Pageable pageable);

 	@RequestMapping(value = "/${rest.client.apilevel}/trips/pageFiltered")
	Page<Trip> listFilteredTrips(@RequestBody TripFilterVO filter, @SpringQueryMap Pageable pageable);

 	@RequestMapping(value = "/${rest.client.apilevel}/trips/count")
	long count();

 	@RequestMapping(value = "/${rest.client.apilevel}/trips/countFiltered")
	long countFilteredBy(@RequestBody TripFilterVO filter);

 	@RequestMapping(value = "/${rest.client.apilevel}/trips/create", method = RequestMethod.PUT)
	long create(@RequestBody Trip trip);

 	@RequestMapping(value = "/${rest.client.apilevel}/trips/update", method = RequestMethod.PATCH)
	long update(@RequestBody Trip trip);

 	@RequestMapping(value = "/${rest.client.apilevel}/trips/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") Long id);

 	@RequestMapping(value = "/${rest.client.apilevel}/trips/{id}")
	Trip get(@PathVariable("id") Long id);

	// @seife auto-code end
}
