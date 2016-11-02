package com.payworks.restapi.repository;

import com.payworks.restapi.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<Hero, String> {
    Hero getById(Integer id);
    Hero getByPseudonym(String pseudonym);
}