package blackJackFX.model.game;


import java.net.URL;
import java.util.Map;


public class FranceCard implements Card {
    private String value;
    private String code;
    private String suit;
    private URL image;
    private Map<String, URL> images;


    public FranceCard(String value, String code, String suit, URL image, Map<String, URL> images) {
        this.value = value;
        this.code = code;
        this.suit = suit;
        this.image = image;
        this.images = images;
    }
    @Override
    public int getIntValue(){
        String value = this.value;

        if(value.equals("ACE")){
            return 11;
        }else if(value.length()>1){
            return 10;
        }else{
            return Integer.parseInt(value);
        }
    }

    public String getValue() { return value; }

    public void setValue(String value) {
        this.value = value;
    }
    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    @Override
    public URL getImageUrl() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }

    public Map<String, URL> getImages() {
        return images;
    }

    public void setImages(Map<String, URL> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Card{" +
                "value='" + value + '\'' +
                ", code='" + code + '\'' +
                ", suit='" + suit + '\'' +
                ", image=" + image +
                ", images=" + images +
                '}';
    }
}
