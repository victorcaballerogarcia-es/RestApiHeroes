package com.payworks.restapi.controller;

import com.payworks.restapi.entity.Hero;
import com.payworks.restapi.dto.HeroDto;
import com.payworks.restapi.repository.HeroRepository;
import com.payworks.restapi.service.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    static final String MESSAGE_HERO_CREATED = "Hero created and persisted correctly";
    static final String MESSAGE_HERO_NOT_CREATED = "Expected date format is YYYY-MM-DD. Hero was not created nor persisted correctly";

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private Converter<Hero, HeroDto> converter;

    @RequestMapping(value = "/heroes", method = RequestMethod.GET)
    public ResponseEntity<List<HeroDto>> getHeroes() {
        List<Hero> heroList = heroRepository.findAll();
        List<HeroDto> heroDtoList = heroList.stream().map(converter::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(heroDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/heroes/{id}", method = RequestMethod.GET)
    public ResponseEntity<HeroDto> getHeroById(@PathVariable("id") Integer id) {
        Hero hero = heroRepository.getById(id);
        return new ResponseEntity<>(converter.convertToDTO(hero), HttpStatus.OK);
    }

    @RequestMapping(value = "/heroes/pseudonym/{pseudonym}", method = RequestMethod.GET)
    public ResponseEntity<HeroDto> getHeroByPseudonym(@PathVariable("pseudonym") String pseudonym) {
        Hero hero = heroRepository.getByPseudonym(pseudonym);
        return new ResponseEntity<>(converter.convertToDTO(hero), HttpStatus.OK);
    }

    @RequestMapping(value = "/heroes", method = RequestMethod.POST)
    public ResponseEntity<String> createHero(@RequestBody @NotNull HeroDto dto) {
        Hero hero = null;
        try {
            hero = converter.convertToDO(dto);
        } catch (ParseException e) {
            return new ResponseEntity<>(MESSAGE_HERO_NOT_CREATED, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        heroRepository.save(hero);
        return new ResponseEntity<>(MESSAGE_HERO_CREATED, HttpStatus.OK);
    }

}

