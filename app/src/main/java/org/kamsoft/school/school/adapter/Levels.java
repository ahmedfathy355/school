package org.kamsoft.school.school.adapter;

public class Levels {

    String text;
    Integer itemId;
    public Levels(String text, Integer itemId){
        this.text=text;
        this.itemId=itemId;
    }

    public String getText(){
        return text;
    }

    public Integer getImageId(){
        return itemId;
    }
}
