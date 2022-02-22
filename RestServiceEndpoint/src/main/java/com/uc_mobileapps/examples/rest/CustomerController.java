package com.uc_mobileapps.examples.rest;

import com.uc_mobileapps.examples.entities.Customer;
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

@SeifeClass
@RestController
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final EntityService entityService;

    @Autowired
    public CustomerController(EntityService entityService) {
        this.entityService = entityService;
    }

    @SeifeMethod
    @RequestMapping("/v1/customers/page")
    public Page<Customer> listCustomers(
            @PageableDefault(page = 0, size = 20)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            })
                    Pageable pageable) {
        logger.info("Get customers offset {} page {}/{}", pageable.getOffset(), pageable.getOffset(), pageable.getPageSize());
        return entityService.findCustomers(pageable);
    }


    @SeifeMethod
    @RequestMapping("/v1/customers/count")
    public long count() {
        long num = entityService.countCustomers();
        logger.info("Found {} customers", num);
        return num;
    }

    /**
     * Creates a new entity or updates it if it is already present.
     * @param customer
     * @return
     */
    @SeifeMethod
    @RequestMapping(value = "/v1/customers/create", method = RequestMethod.PUT)
    public long create(@RequestBody Customer customer) {
        Customer created = entityService.saveCustomer(customer);
        return created.getId();
    }

    /**
     * Patches an existing entity by updating all non-null values.
     * @param customer instance with that id must exist
     * @return
     */
    @SeifeMethod
    @RequestMapping(value = "/v1/customers/update", method = RequestMethod.PATCH)
    public long update(@RequestBody Customer customer) {
        Customer created = entityService.updateCustomer(customer);
        return created.getId();
    }

    /**
     * Creates a new entity or updates it if it is already present.
     * @param id entity id to delete
     */
    @SeifeMethod
    @RequestMapping(value = "/v1/customers/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        if (id != null) {
            entityService.deleteCustomer(id);
        }
    }

    @SeifeMethod
    @RequestMapping("/v1/customers/{id}")
    public Customer get(@PathVariable("id") Long id) {
        return entityService.getCustomerById(id);
    }

}
