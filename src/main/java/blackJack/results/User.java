package blackJack.results;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Class representing the result of a game played by a specific player.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,  unique=true)
    private String username;

    @Column(nullable = false)
    private String password;

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

    @Version
    @Setter(AccessLevel.NONE)
    private long version;

    @Column(nullable = false)
    private ZonedDateTime created;

    @PrePersist
    protected void onPersist() {
        created = ZonedDateTime.now();
    }
}
