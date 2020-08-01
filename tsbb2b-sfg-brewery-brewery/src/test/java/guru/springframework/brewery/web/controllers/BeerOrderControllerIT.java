package guru.springframework.brewery.web.controllers;


import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.repositories.BeerOrderRepository;
import guru.springframework.brewery.repositories.CustomerRepository;
import guru.springframework.brewery.web.model.BeerOrderPagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerOrderControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll().get(0); //because our bootstrap class adds 1
    }

    @Test
    void testListOrders() {
        String url = "/api/v1/customers/" + customer.getId() + "/orders";

        BeerOrderPagedList pagedList = restTemplate.getForObject(url, BeerOrderPagedList.class);
        assertThat(pagedList.getContent()).hasSize(1);
    }
}
