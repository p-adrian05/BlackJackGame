package com.company.blackJack.card;

import lombok.*;


import java.net.URL;

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
    @Getter(AccessLevel.NONE)
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
    public URL getImageUrl() {
        return image;
    }
}
