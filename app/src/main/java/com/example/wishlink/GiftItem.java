package com.example.wishlink;

public class GiftItem {

    private String nombre;
    private String link1;
    private String link2;

    public GiftItem(String nombre,
                    String link1,
                    String link2) {

        this.nombre = nombre;
        this.link1 = link1;
        this.link2 = link2;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLink1() {
        return link1;
    }

    public String getLink2() {
        return link2;
    }
}