package com.bauet.bauet;

public class bChat {
    private  String meassge;
    private  String author;
    private  String time;

    public bChat(String meassge, String author,String time) {
        this.meassge = meassge;
        this.author = author;
        this.time = time;
    }

    public bChat() {


    }

    public String getMeassge() {
        return meassge;
    }

    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }
}
