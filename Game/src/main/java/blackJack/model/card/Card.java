package blackJack.model.card;

import java.net.URL;

/**
 * The Card interface provides four methods to get the most needed attributes from an implementation.
 */

public interface Card {

    int getIntValue();
    String getValue();
    String getCode();
    URL getImageUrl();

}
