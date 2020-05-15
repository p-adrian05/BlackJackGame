package blackJack.model.game;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.Map;

/**
 * Class represents an implementation of {@link Card} interface.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FranceCard implements Card {

    private String value;
    private String code;
    private String suit;
    private URL image;

    /**
     * Returns an {@code int} representation of {@code String} {@link #value} attribute.
     *
     * @return a value of a {@link Card} object
     */
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

    @Override
    public String getValue() { return value; }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public URL getImageUrl() {
        return image;
    }

}
