package com.company.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
/**
 * Class representing a the actual {@link User} data in the game.
 */
@Data
@AllArgsConstructor
@Entity
public class GameData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int funds;

    @Column(nullable = false)
    private int lostMoney;

    @Column(nullable = false)
    private int wonMoney;

    @Column(nullable = false)
    private int wonCount;

    @Column(nullable = false)
    private int loseCount;

    @Column(nullable = false)
    private int maxBet;

    @OneToOne(mappedBy = "gameData",fetch = FetchType.LAZY )
    private User user;

    public GameData(){
        funds = 0;
        lostMoney = 0;
        wonMoney = 0;
        loseCount = 0;
        wonCount = 0;
        maxBet = 0;
    }
}
