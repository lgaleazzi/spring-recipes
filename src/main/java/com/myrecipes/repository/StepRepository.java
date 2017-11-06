package com.myrecipes.repository;

import com.myrecipes.model.Step;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends CrudRepository<Step, Long>
{
}
