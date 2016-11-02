package com.payworks.restapi.service;

import com.payworks.restapi.entity.Hero;
import com.payworks.restapi.entity.Skill;
import com.payworks.restapi.dto.HeroDto;
import com.payworks.restapi.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeroConverter implements Converter<Hero, HeroDto> {

    @Autowired
    private HeroRepository heroRepository;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public HeroConverter() {
    }

    HeroConverter(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public HeroDto convertToDTO(Hero hero) {
        if (hero != null) {
            List<String> skillList = hero.getSkills().stream()
                    .map(Skill::getDescription)
                    .collect(Collectors.toList());
            List<String> allyList = hero.getAllies().stream()
                    .map(Hero::getPseudonym)
                    .collect(Collectors.toList());
            String firstAppearanceString = (hero.getFirstAppearance() != null) ? formatter.format(hero.getFirstAppearance()) : null;
            return new HeroDto(hero.getName(), hero.getPseudonym(), hero.getPublisher(), skillList, allyList, firstAppearanceString);
        }
        return null;
    }

    public Hero convertToDO(HeroDto heroDto) throws ParseException {
        if (heroDto != null) {
            Date firstAppearanceDate = (heroDto.getFirstAppearance() != null) ? formatter.parse(heroDto.getFirstAppearance()) : null;
            Hero hero = new Hero(heroDto.getName(), heroDto.getPseudonym(), heroDto.getPublisher(), firstAppearanceDate);
            List<Skill> skillList = heroDto.getSkills().stream()
                    .map(s -> new Skill(hero, s))
                    .collect(Collectors.toList());
            hero.setSkills(skillList);
            List<Hero> allyList = heroDto.getAllies().stream()
                    .map(heroRepository::getByPseudonym)
                    .filter(ally -> ally != null)
                    .collect(Collectors.toList());
            hero.setAllies(allyList);
            return hero;
        }
        return null;
    }

    public static final SimpleDateFormat getSimpleDateFormatter() {
        return formatter;
    }

}
