package com.payworks.restapi.repository;

import com.payworks.restapi.IntegrationConfigurationTest;
import com.payworks.restapi.entity.Hero;
import com.payworks.restapi.entity.Skill;
import com.payworks.restapi.service.HeroConverter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class HeroRepositoryTest extends IntegrationConfigurationTest {

    /*For testing purposes import.sql script inserts 2 heroes on database: Robin and Batman*/
    private static final int DEFAULT_TEST_DB_URLS_REGS = 2;
    private static final Integer TEST_FETCH_HERO_ID = 2;
    private static final String TEST_FETCH_HERO_NAME = "Bruce Wayne";
    private static final String TEST_FETCH_HERO_PSEUDONYM = "Batman";
    private static final String TEST_FETCH_HERO_PUBLISHER = "DC";
    private static final Integer TEST_FETCH_HERO_SKILLS_SIZE = 5;
    private static final Integer TEST_FETCH_HERO_ALLIES_SIZE = 1;
    private static final String TEST_FETCH_HERO_FIRST_APPEARANCE = "1939-05-01";

    private static final String TEST_SUPERGIRL_NAME = "Linda Lee";
    private static final String TEST_SUPERGIRL_PSEUDONYM = "Supergirl";
    private static final String TEST_SUPERGIRL_PUBLISHER = "DC";
    private static final List<String> TEST_SUPERGIRL_SKILLS = Arrays.asList("Vast superhuman strength", "Invulnerability", "Flight");
    private static final Integer TEST_SUPERGIRL_ALLIES_SIZE = 0;
    private static final String TEST_SUPERGIRL_FIRST_APPEARANCE = "1959-05-01";

    private static final String TEST_SUPERMAN_NAME = "Clark Kent";
    private static final String TEST_SUPERMAN_PSEUDONYM = "Superman";
    private static final String TEST_SUPERMAN_PUBLISHER = "DC";
    private static final List<String> TEST_SUPERMAN_SKILLS = Arrays.asList("Solar energy absorption and healing factor", "Solar flare and heat vision", "Superlongevity", "Flight");
    private static final Integer TEST_SUPERMAN_ALLIES_SIZE = 1;
    private static final String TEST_SUPERMAN_FIRST_APPEARANCE = "1939-05-01";

    @Autowired
    HeroRepository heroRepository;

    @Transactional
    @Test
    public void testFindAll() {
        List<Hero> list = heroRepository.findAll();
        assertThat(list.size(), is(DEFAULT_TEST_DB_URLS_REGS));
    }

    @Transactional
    @Test
    public void testGetById() throws ParseException {
        Hero hero = heroRepository.getById(TEST_FETCH_HERO_ID);
        assertThat(hero, notNullValue());
        assertThat(hero.getId(), equalTo(TEST_FETCH_HERO_ID));
        assertThat(hero.getName(), equalTo(TEST_FETCH_HERO_NAME));
        assertThat(hero.getPseudonym(), equalTo(TEST_FETCH_HERO_PSEUDONYM));
        assertThat(hero.getPublisher(), equalTo(TEST_FETCH_HERO_PUBLISHER));
        assertThat(hero.getSkills().size(), equalTo(TEST_FETCH_HERO_SKILLS_SIZE));
        assertThat(hero.getAllies().size(), equalTo(TEST_FETCH_HERO_ALLIES_SIZE));
        assertThat(HeroConverter.getSimpleDateFormatter().format(hero.getFirstAppearance()), equalTo(TEST_FETCH_HERO_FIRST_APPEARANCE));
    }

    @Transactional
    @Test
    public void testGetByPseudonym() throws ParseException {
        Hero hero = heroRepository.getByPseudonym(TEST_FETCH_HERO_PSEUDONYM);
        assertThat(hero, notNullValue());
        assertThat(hero.getId(), equalTo(TEST_FETCH_HERO_ID));
        assertThat(hero.getName(), equalTo(TEST_FETCH_HERO_NAME));
        assertThat(hero.getPseudonym(), equalTo(TEST_FETCH_HERO_PSEUDONYM));
        assertThat(hero.getPublisher(), equalTo(TEST_FETCH_HERO_PUBLISHER));
        assertThat(hero.getSkills().size(), equalTo(TEST_FETCH_HERO_SKILLS_SIZE));
        assertThat(hero.getAllies().size(), equalTo(TEST_FETCH_HERO_ALLIES_SIZE));
        assertThat(HeroConverter.getSimpleDateFormatter().format(hero.getFirstAppearance()), equalTo(TEST_FETCH_HERO_FIRST_APPEARANCE));
    }

    public Hero createHeroine() throws ParseException {
        Hero heroine = new Hero(TEST_SUPERGIRL_NAME, TEST_SUPERGIRL_PSEUDONYM, TEST_SUPERGIRL_PUBLISHER, HeroConverter.getSimpleDateFormatter().parse(TEST_SUPERGIRL_FIRST_APPEARANCE));
        List<Skill> skillList = new ArrayList<>();
        for (String s : TEST_SUPERGIRL_SKILLS) {
            skillList.add(new Skill(heroine, s));
        }
        heroine.setSkills(skillList);
        return heroine;
    }

    public Hero createHero() throws ParseException {
        Hero hero = new Hero(TEST_SUPERMAN_NAME, TEST_SUPERMAN_PSEUDONYM, TEST_SUPERMAN_PUBLISHER, HeroConverter.getSimpleDateFormatter().parse(TEST_SUPERMAN_FIRST_APPEARANCE));
        List<Skill> skillList = new ArrayList<>();
        for (String s : TEST_SUPERMAN_SKILLS) {
            skillList.add(new Skill(hero, s));
        }
        hero.setSkills(skillList);
        return hero;
    }

    @Transactional
    @Test
    public void testSaveHero() throws ParseException {
        Hero heroine = createHeroine();
        heroine = heroRepository.save(heroine);

        assertThat(heroRepository.findAll().size(), equalTo(DEFAULT_TEST_DB_URLS_REGS + 1));
        assertThat(heroine.getId(), notNullValue());
        assertThat(heroine.getName(), equalTo(TEST_SUPERGIRL_NAME));
        assertThat(heroine.getPseudonym(), equalTo(TEST_SUPERGIRL_PSEUDONYM));
        assertThat(heroine.getPublisher(), equalTo(TEST_SUPERGIRL_PUBLISHER));
        assertThat(heroine.getSkills().size(), equalTo(TEST_SUPERGIRL_SKILLS.size()));
        assertThat(heroine.getAllies().size(), equalTo(TEST_SUPERGIRL_ALLIES_SIZE));
        assertThat(HeroConverter.getSimpleDateFormatter().format(heroine.getFirstAppearance()), equalTo(TEST_SUPERGIRL_FIRST_APPEARANCE));

        Hero hero = createHero();
        hero.setAllies(Arrays.asList(heroine));
        hero = heroRepository.save(hero);

        assertThat(heroRepository.findAll().size(), equalTo(DEFAULT_TEST_DB_URLS_REGS + 2));
        assertThat(hero.getId(), notNullValue());
        assertThat(hero.getName(), equalTo(TEST_SUPERMAN_NAME));
        assertThat(hero.getPseudonym(), equalTo(TEST_SUPERMAN_PSEUDONYM));
        assertThat(hero.getPublisher(), equalTo(TEST_SUPERMAN_PUBLISHER));
        assertThat(hero.getSkills().size(), equalTo(TEST_SUPERMAN_SKILLS.size()));

        assertThat(hero.getSkills().get(0).getId(), notNullValue());
        assertThat(hero.getSkills().get(0).getHero(), equalTo(hero));
        assertThat(hero.getSkills().get(0).getDescription(), equalTo(TEST_SUPERMAN_SKILLS.get(0)));

        assertThat(hero.getAllies().size(), equalTo(TEST_SUPERMAN_ALLIES_SIZE));
        assertThat(hero.getAllies().get(0).getPseudonym(), equalTo(TEST_SUPERGIRL_PSEUDONYM));
        assertThat(HeroConverter.getSimpleDateFormatter().format(hero.getFirstAppearance()), equalTo(TEST_SUPERMAN_FIRST_APPEARANCE));
    }

    @Transactional
    @Test(expected = PersistenceException.class)
    public void testSaveHeroUniqueness() throws ParseException {
        Hero hero = createHero();
        hero = heroRepository.save(hero);
        Hero sameHero = createHero();
        sameHero = heroRepository.save(sameHero);
    }

}
