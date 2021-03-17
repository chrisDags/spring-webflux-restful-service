package com.dags.springwebfluxrestful.controllers;

import com.dags.springwebfluxrestful.domain.Category;
import com.dags.springwebfluxrestful.repository.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//todo: Refactor business logic and repository calls into services for both controller classes

@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/v1/categories")
    Flux<Category> list(){
        return  categoryRepository.findAll();
    }

    @GetMapping("/api/v1/categories/{id}")
    Mono<Category> getById(@PathVariable String id){
        return categoryRepository.findById(id);
    }

    // categoryPublisher is a stream
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/categories")
    Mono<Void> create(@RequestBody Publisher<Category> categoryPublisher){

        return categoryRepository.saveAll(categoryPublisher).then();
    }

    @PutMapping("/api/v1/categories/{id}")
    Mono<Category> update(@PathVariable String id, @RequestBody Category category){
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping("/api/v1/categories/{id}")
    Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {

        Category category1 = categoryRepository.findById(id).block();

        if (category1.getDescription() != category.getDescription()) {
            category1.setDescription(category.getDescription());
            return categoryRepository.save(category1);
        }

        return Mono.just(category1);
    }
}
