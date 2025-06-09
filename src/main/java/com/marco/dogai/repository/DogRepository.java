package com.marco.dogai.repository;

import com.marco.dogai.entity.Dog;
import org.springframework.data.repository.ListCrudRepository;

public interface DogRepository extends ListCrudRepository<Dog, Integer> {
}