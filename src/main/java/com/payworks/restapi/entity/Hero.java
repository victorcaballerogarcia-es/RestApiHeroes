package com.payworks.restapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hero")
public class Hero implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "pseudonym", unique = true)
    private String pseudonym;

    @Column(name = "publisher")
    private String publisher;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hero", cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonManagedReference
    private List<Skill> skills = new ArrayList<Skill>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "hero_ally",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "hero_id")})
    private List<Hero> allies = new ArrayList<Hero>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "allies")
    private List<Hero> alliedwith = new ArrayList<Hero>();

    @Column(name = "first_appearance")
    private Date firstAppearance;

    public Hero() {
    }

    public Hero(String name, String pseudonym, String publisher, Date firstAppearance) {
        this.name = name;
        this.pseudonym = pseudonym;
        this.publisher = publisher;
        this.firstAppearance = firstAppearance;
    }

    public Integer getId() {
        return id;
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

    public List<Skill> getSkills() {
        return skills;
    }

    public List<Hero> getAllies() {
        return allies;
    }

    public List<Hero> getAlliedwith() {
        return alliedwith;
    }

    public Date getFirstAppearance() {
        return firstAppearance;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void setAllies(List<Hero> allies) {
        this.allies = allies;
    }

}
