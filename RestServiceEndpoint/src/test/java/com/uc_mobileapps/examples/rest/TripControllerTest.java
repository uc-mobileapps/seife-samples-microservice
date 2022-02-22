package com.uc_mobileapps.examples.rest;

import com.uc_mobileapps.examples.rest.dao.CustomerRepository;
import com.uc_mobileapps.examples.rest.dao.TripRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TripController.class)
@Import(EntityService.class)
public class TripControllerTest {

    @MockBean
    private TripRepository tripRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void pageTrips() throws Exception {

        mockMvc.perform(get("/v1/trips/page")
                        .param("page", "5")
                        .param("size", "10")
                        .param("sort", "id,desc"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(tripRepository).findAll(pageableCaptor.capture());
        PageRequest pageable = (PageRequest) pageableCaptor.getValue();

        assertThat(pageable.getPageNumber()).isEqualTo(5);
        assertThat(pageable.getPageSize()).isEqualTo(10);
        assertThat(pageable.getSort().getOrderFor("id").getDirection()).isEqualTo(Sort.Direction.DESC);
    }
}
