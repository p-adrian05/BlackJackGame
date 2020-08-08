package com.company.blackJack.card;

import java.util.Optional;

/**
 * The CardApi interface provides to get an implementation of {@link Deck} interface.
 */

public interface CardApi {

     /**
      * Provides a {@link Deck} object, which is made from an API call response.
      * @return {@link Deck} object, wrapped in {@link Optional} object.
      */
     Optional<Deck> getDeck();

}
