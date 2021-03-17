package com.dags.springwebfluxrestful.controllers;

import com.dags.springwebfluxrestful.domain.Vendor;
import com.dags.springwebfluxrestful.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void list() {
        given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder()
                                .firstName("somefirstname")
                                .lastName("somelastname")
                                .build(),
                        Vendor.builder()
                                .firstName("somefirstname1")
                                .lastName("somelastname1")
                                .build()));

        webTestClient.get().uri("/api/v1/vendors/").exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        given(vendorRepository.findById("someId"))
                .willReturn(Mono.just(Vendor.builder()
                        .firstName("first")
                        .lastName("last")
                        .build()));

        webTestClient.get().uri("/api/v1/vendors/someId")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    public void testCreateVendor(){
        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorMonoToSave = Mono.just(Vendor.builder()
                .firstName("first name")
                .lastName("last name")
                .build());

        webTestClient.post().uri("/api/v1/vendors/")
                .body(vendorMonoToSave, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdate(){
        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMonoToUpdate = Mono.just(Vendor.builder()
                .firstName("Some First")
                .lastName("Some Last")
                .build());

        webTestClient.put()
                .uri("/api/v1/vendors/abcdefg")
                .body(vendorMonoToUpdate, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}