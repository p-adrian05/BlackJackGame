package com.company.blackJack.card;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Class represents an implementation of {@link CardApi} interface,
 * obtaining {@link Card} objects and made an implementation {@link Deck} interface.
 * The class uses the <a href = "https://deckofcardsapi.com">deckofcardsapi.com</a> service.
 */
@Service
public class FranceCardApi implements CardApi{

    /**
     * URI of the card api service.
     */
    private static final String DECK_URI = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Returns a {@link DeckFranceCards} implementation of {@link Deck} interface uses the
     * api service if it is successfully returns, otherwise read the data from a local
     * json file.
     *
     * @return {@link DeckFranceCards} object
     */
    @Override
    public Optional<Deck> getDeck(){
        DeckFranceCards deck;
        String cardsRes;
        try {
            URL CARDS_URL = makeCardsUrl(getDeckID());
            cardsRes = IOUtils.toString(CARDS_URL, StandardCharsets.UTF_8);
            if(requestIsSuccess(cardsRes)){
                deck = GSON.fromJson(cardsRes, DeckFranceCards.class);
            }else{
                deck = GSON.fromJson(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("cards.json")), DeckFranceCards.class);
            }
        } catch (IOException e) {
            deck = GSON.fromJson(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("cards.json")), DeckFranceCards.class);
            e.printStackTrace();
        }
        if(deck!=null){
            deck.shuffle();
        }
        return Optional.ofNullable(deck);
    }
    /**
     * Returns a {@code String} data used the {@value #DECK_URI} URI to get the service ID
     * for further api calls.
     *
     * @return {@code String} id information
     */
    private String getDeckID(){
        String deckRes;
        String deckID = "";
        try {
            deckRes = IOUtils.toString(URI.create(DECK_URI),"UTF-8");
            if(requestIsSuccess(deckRes)){
                JsonObject json = JsonParser.parseString(deckRes).getAsJsonObject();
                deckID = json.getAsJsonPrimitive("deck_id").getAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deckID;
    }

    /**
     * Made an {@link URL} object from the needed id information.
     *
     * @param deckID the {@code String} data provided by {@link #getDeckID()}
     * @return {@link URL} object
     * @throws MalformedURLException if the URL creation fails
     */
    private URL makeCardsUrl(String deckID) throws MalformedURLException {
        return new URL("https://deckofcardsapi.com/api/deck/"+deckID+"/draw/?count=52");
    }

    /**
     * Returns the given {@code String} URI response success attribute value.
     *
     * @param jsonString the URI response
     * @return {@code true} if the success attribute is true, {@code false} otherwise
     * @throws AssertionError if the given {@code String} response not contains success attributes
     * or the value of it is not true or false
     * */
    private boolean requestIsSuccess(String jsonString){
        JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
        String success = json.getAsJsonPrimitive("success").getAsString();
        return success.equals("true");
    }
}
