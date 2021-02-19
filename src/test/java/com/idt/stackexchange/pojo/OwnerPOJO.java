package com.idt.stackexchange.pojo;

public class OwnerPOJO {
    private String display_name;
    private int user_id;
    private String link;

    public String getDisplay_name() {
        return display_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getLink() {
        return link;
    }

    public String getIdToString() {
        return user_id + "";
    }
}
