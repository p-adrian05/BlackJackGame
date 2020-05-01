package blackJackFX.Model;

import java.net.URL;
import java.util.Map;
import lombok.*;

@Data
public class Card {
    private String value;
    private String code;
    private String suit;
    private URL image;
    private Map<String, URL> images;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

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

    public URL getImage() {
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
}
