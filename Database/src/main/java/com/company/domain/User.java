package com.company.domain;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Class representing a User in the game.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,  unique=true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "gamedata_id",referencedColumnName = "id",nullable = false)
    private GameData gameData;

    @Column(nullable = false)
    private ZonedDateTime created;

    @PrePersist
    protected void onPersist() {
        created = ZonedDateTime.now();
    }

    public User(String username, String password, GameData gameData) {
        this.username = username;
        this.password = password;
        this.gameData = gameData;
    }
}
