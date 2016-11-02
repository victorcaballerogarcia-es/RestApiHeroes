package com.payworks.restapi.dto;

import java.util.ArrayList;
import java.util.List;

public class HeroDto {

    private String name;
    private String pseudonym;
    private String publisher;
    private List<String> skills = new ArrayList<String>();
    private List<String> allies = new ArrayList<String>();
    private String firstAppearance;

    public HeroDto() {
    }

    public HeroDto(String name, String pseudonym, String publisher, List<String> skills, List<String> allies, String firstAppearance) {
        this.name = name;
        this.pseudonym = pseudonym;
        this.publisher = publisher;
        this.skills = skills;
        this.allies = allies;
        this.firstAppearance = firstAppearance;
    }

    public String getName() {
        return name;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public String getPublisher() {
        return publisher;
    }

    public List<String> getSkills() {
        return skills;
    }

    public List<String> getAllies() {
        return allies;
    }

    public String getFirstAppearance() {
        return firstAppearance;
    }
}
