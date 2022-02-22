package com.uc_mobileapps.examples.restclient;

import com.uc_mobileapps.examples.entities.Trip;
import com.weebmeister.seife.anno.SeifeClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uc_mobileapps.examples.entities.Customer;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.cloud.openfeign.SpringQueryMap;

/**
 * This class is automatically maintained based on TripController in the RestClient project.
 */
@SeifeClass(classOptions = { "sourcemodel:class=com.uc_mobileapps.examples.rest.CustomerController," +
        "prefix=/v1/customers/,replacement=/${rest.client.apilevel}/customers/" },
        generatorOptions = {
		// generate the feign client service interface methods:
        "merge.feignclient/client.vm=com.uc_mobileapps.examples.restclient.CustomerClient",
})
@FeignClient(name = "customerclient", fallback = Object.class)
public interface CustomerClient {


	// @seife automatically generated:

 	@RequestMapping(value = "/${rest.client.apilevel}/customers/page")
	Page<Customer> listCustomers(@SpringQueryMap Pageable pageable);

 	@RequestMapping(value = "/${rest.client.apilevel}/customers/count")
	long count();

 	@RequestMapping(value = "/${rest.client.apilevel}/customers/create", method = RequestMethod.PUT)
	long create(@RequestBody Customer customer);

 	@RequestMapping(value = "/${rest.client.apilevel}/customers/update", method = RequestMethod.PATCH)
	long update(@RequestBody Customer customer);

 	@RequestMapping(value = "/${rest.client.apilevel}/customers/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") Long id);

 	@RequestMapping(value = "/${rest.client.apilevel}/customers/{id}")
	Customer get(@PathVariable("id") Long id);

	// @seife auto-code end
}
