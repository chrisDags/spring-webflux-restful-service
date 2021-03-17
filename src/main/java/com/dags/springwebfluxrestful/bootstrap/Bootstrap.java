package com.dags.springwebfluxrestful.bootstrap;

import com.dags.springwebfluxrestful.domain.Category;
import com.dags.springwebfluxrestful.domain.Vendor;
import com.dags.springwebfluxrestful.repository.CategoryRepository;
import com.dags.springwebfluxrestful.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count().block() == 0) {
            System.out.println("LOADING DATA ON BOOTSTRAP");

            categoryRepository.save(Category.builder()
                    .description("Fruits").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Nuts").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Bread").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Meats").build()).block();

            System.out.println("Loaded Categories: " + categoryRepository.count().block());


            vendorRepository.save(Vendor.builder()
                    .firstName("Chris")
                    .lastName("Dags")
                    .build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Foo")
                    .lastName("Bar")
                    .build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Jimmy")
                    .lastName("Buffett")
                    .build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Luke")
                    .lastName("Skywalker")
                    .build()).block();

            System.out.println("Loaded Vendors: " + vendorRepository.count().block());
        }


    }
}
