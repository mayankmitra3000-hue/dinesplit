package com.mayank.dinesplit.repository;

import com.mayank.dinesplit.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
