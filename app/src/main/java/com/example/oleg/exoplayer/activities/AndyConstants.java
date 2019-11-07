package com.example.oleg.exoplayer.activities;

public class AndyConstants {

    public class ServiceCode {
        public static final int HOME = 1;
    }
    // web service url constants
    public class ServiceType {
          //http://localhost/revolut/
          //public static final String URL = "http://jogavisiem.lv/test1/";
          //public static final String URL = "http://jogavisiem.lv/test/";
          public static final String URL = "https://revolut.duckdns.org/latest?base=EUR";
          //public static final String URL = "http://192.168.1.176/revolut/";
          //public static final String URL = "http://localhost/revolut/";
          //public static final String URL = "http://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php";

   }
    // webservice key constants
    public class Params {

        /*
        curID long
        CurBase String
        rateDate String
        curName String
        curRate double
        */

        //public static final String curID = "cur_id";
        //public static final String curBase = "base";
        //public static final String rateDate = "date";
        public static final String curName = "rates1";
        public static final String curRate = "rates2";

        //public static final String ID = "id";
        //public static final String NAME = "name";
        //public static final String COUNTRY = "country";
        //public static final String CITY = "city";

       }
}

