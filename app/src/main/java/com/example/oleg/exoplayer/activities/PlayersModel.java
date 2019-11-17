package com.example.oleg.exoplayer.activities;

public class PlayersModel {

    private long curID; //long
    private String CurBase; //String
    private String rateDate; //String
    private String curName; //String
    private String curRate; //double
    private String curDesc; //
    private String curUrlFlag;

    public long getCurID() {
        return curID;
    }

    public void setCurID(long curID) {
        this.curID = curID;
    }

    public String getCurBase() {
        return CurBase;
    }

    public void setCurBase(String curBase) {
        CurBase = curBase;
    }

    public String getRateDate() {
        return rateDate;
    }

    public void setRateDate(String rateDate) {
        this.rateDate = rateDate;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public String getCurRate() {
        return curRate;
    }

    public void setCurRate(String curRate) {
        this.curRate = curRate;
    }

    public String getCurDesc() {
        return curDesc;
    }

    public void setCurDesc(String curDesc) {
        this.curDesc = curDesc;
    }

    //private String name, country, city;

    /*
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    */
}
