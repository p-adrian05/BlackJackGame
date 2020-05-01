package blackJackFX.Game;

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


}
