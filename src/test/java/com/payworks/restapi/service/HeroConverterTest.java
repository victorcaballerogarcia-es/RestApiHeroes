package com.payworks.restapi.service;

import com.payworks.restapi.entity.Hero;
import com.payworks.restapi.dto.HeroDto;
import com.payworks.restapi.entity.Skill;
import com.payworks.restapi.repository.HeroRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HeroConverterTest {

    @Mock
    Hero heroMock;

    @Mock
    HeroDto heroDtoMock;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDTOtoDO() throws ParseException {
        when(heroDtoMock.getName()).thenReturn("Bruce Wayne");
        when(heroDtoMock.getPseudonym()).thenReturn("Batman");
        when(heroDtoMock.getPublisher()).thenReturn("DC");
        when(heroDtoMock.getSkills()).thenReturn(Arrays.asList("Genius-level intellect", "Peak human physical and mental conditioning", "Expert martial artist and hand-to-hand combatant", "Expert detective", "Utilizes high-tech equipment and weapons"));
        when(heroDtoMock.getAllies()).thenReturn(Arrays.asList("Robin"));
        when(heroDtoMock.getFirstAppearance()).thenReturn("1939-05-01");

        HeroRepository spyRepository = mock(HeroRepository.class, Mockito.CALLS_REAL_METHODS);
        Hero robin = new Hero("Dick Grayson", "Robin", "DC", HeroConverter.getSimpleDateFormatter().parse("1940-04-01"));
        when(spyRepository.getByPseudonym("Robin")).thenReturn(robin);

        HeroConverter converter = new HeroConverter(spyRepository);
        Hero hero = converter.convertToDO(heroDtoMock);

        Assert.assertEquals(heroDtoMock.getName(), hero.getName());
        Assert.assertEquals(heroDtoMock.getPseudonym(), hero.getPseudonym());
        Assert.assertEquals(heroDtoMock.getPublisher(), hero.getPublisher());
        Assert.assertEquals(heroDtoMock.getSkills().size(), hero.getSkills().size());
        Assert.assertEquals(heroDtoMock.getAllies().size(), hero.getAllies().size());
        Assert.assertEquals(HeroConverter.getSimpleDateFormatter().parse(heroDtoMock.getFirstAppearance()), hero.getFirstAppearance());
    }

    @Test
    public void testNullDTOtoDO() throws ParseException {
        Hero hero = new HeroConverter().convertToDO(null);
        Assert.assertNull(hero);
    }

    @Test
    public void testDOtoDTO() throws ParseException {
        when(heroMock.getName()).thenReturn("Bruce Wayne");
        when(heroMock.getPseudonym()).thenReturn("Batman");
        when(heroMock.getPublisher()).thenReturn("DC");
        List<Skill> skillList = new ArrayList<>();
        skillList.add(new Skill(heroMock, "Genius-level intellect"));
        skillList.add(new Skill(heroMock, "Peak human physical and mental conditioning"));
        skillList.add(new Skill(heroMock, "Expert martial artist and hand-to-hand combatant"));
        skillList.add(new Skill(heroMock, "Expert detective"));
        skillList.add(new Skill(heroMock, "Utilizes high-tech equipment and weapons"));
        when(heroMock.getSkills()).thenReturn(skillList);
        List<Hero> alliesList = new ArrayList<>();

        Hero robin = new Hero("Dick Grayson", "Robin", "DC", HeroConverter.getSimpleDateFormatter().parse("1940-04-01"));
        alliesList.add(robin);
        when(heroMock.getAllies()).thenReturn(alliesList);
        when(heroMock.getFirstAppearance()).thenReturn(HeroConverter.getSimpleDateFormatter().parse("1939-05-01"));

        HeroDto heroDto = new HeroConverter().convertToDTO(heroMock);

        Assert.assertEquals(heroMock.getName(), heroDto.getName());
        Assert.assertEquals(heroMock.getPseudonym(), heroDto.getPseudonym());
        Assert.assertEquals(heroMock.getPublisher(), heroDto.getPublisher());
        Assert.assertEquals(heroMock.getSkills().size(), heroDto.getSkills().size());
        Assert.assertEquals(heroMock.getAllies().size(), heroDto.getAllies().size());
        Assert.assertEquals(HeroConverter.getSimpleDateFormatter().format(heroMock.getFirstAppearance()), heroDto.getFirstAppearance());
    }

    @Test
    public void testNullDOtoDTO() throws ParseException {
        HeroDto heroDto = new HeroConverter().convertToDTO(null);
        Assert.assertNull(heroDto);
    }

}
