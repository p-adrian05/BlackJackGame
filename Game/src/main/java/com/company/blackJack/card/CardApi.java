package com.company.blackJack.card;

import java.util.Optional;

/**
 * The CardApi interface provides to get an implementation of {@link Deck} interface.
 */

public interface CardApi {

     Optional<Deck> getDeck();

}
