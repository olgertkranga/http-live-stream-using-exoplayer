package com.example.oleg.exoplayer.activities;

public class PlayersModel {

    private long curID; //long

    private String CurBase; //String
    private String rateDate; //String
    private String curName; //String
    private String curRate; //double
    private String curDesc; //
    private String curUrlFlag;

    ///
    public PlayersModel(
            String rateDate, //2
            String curName, //3
            String curRate, //4
            String curDesc, //5
            String curUrlFlag) //6
    {

        this.rateDate = rateDate; //2
        this.curName = curName; //3
        this.curRate = curRate; //4
        this.curDesc = curDesc; //5
        this.curUrlFlag = curUrlFlag; //6

    }

    public PlayersModel() {
    }

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

    public String getCurUrlFlag() {
        return curUrlFlag;
    }

    public void setCurUrlFlag(String curUrlFlag) {
        this.curUrlFlag = curUrlFlag;
    }

}
