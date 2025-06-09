package com.marco.dogai.repository;

import com.marco.dogai.entity.DogEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface DogRepository extends ListCrudRepository<DogEntity, Integer> {
}