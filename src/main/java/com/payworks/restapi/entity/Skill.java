package com.payworks.restapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hero_skill")
public class Skill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hero_id", referencedColumnName = "id")
    @JsonBackReference
    private Hero hero;

    @Column(name = "description")
    private String description;

    public Skill() {
    }

    public Skill(Hero hero, String description) {
        this.hero = hero;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public Hero getHero() {
        return this.hero;
    }

    public String getDescription() {
        return description;
    }

}
