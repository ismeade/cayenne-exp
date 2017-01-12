package com.ade.exp.cayenne.mult.persistent.db2;

import com.ade.exp.cayenne.mult.persistent.db2.auto._Db2Map;

public class Db2Map extends _Db2Map {

    private static Db2Map instance;

    private Db2Map() {}

    public static Db2Map getInstance() {
        if(instance == null) {
            instance = new Db2Map();
        }

        return instance;
    }
}
