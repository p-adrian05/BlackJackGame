package blackJackFX.Game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;


public class CardApi {

    private static final String DECK_URL = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private URL CARDS_URL;

    public Optional<Deck> getDeck(){
        Cards deck = null;
        String cardsRes;
        try {
            CARDS_URL = makeCardsUrl(getDeckID());
            cardsRes = IOUtils.toString(CARDS_URL,"UTF-8");
            if(requestIsSuccess(cardsRes)){
                deck = GSON.fromJson(cardsRes, Cards.class);
            }else{
                deck = GSON.fromJson(new FileReader("cards.json"),Cards.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(deck);
    }

    private String getDeckID(){
        String deckRes;
        String deckID = "";
        try {
            deckRes = IOUtils.toString(URI.create(DECK_URL),"UTF-8");
            if(requestIsSuccess(deckRes)){
                JsonObject json = JsonParser.parseString(deckRes).getAsJsonObject();
                deckID = json.getAsJsonPrimitive("deck_id").getAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deckID;
    }

    private URL makeCardsUrl(String deckID) throws MalformedURLException {
        return new URL("https://deckofcardsapi.com/api/deck/"+deckID+"/draw/?count=52");
    }
    private boolean requestIsSuccess(String jsonString){
        JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
        String success = json.getAsJsonPrimitive("success").getAsString();
        if(success.equals("true")) {
            return true;
        }
        else if(success.equals("false")){
            return false;
        }

        throw new AssertionError("Invalid response");
    }
}
