package com.ade.exp.cayenne.mult.persistent.db2;

import com.ade.exp.cayenne.mult.persistent.db2.auto._TestDb2Map;

public class TestDb2Map extends _TestDb2Map {

    private static TestDb2Map instance;

    private TestDb2Map() {}

    public static TestDb2Map getInstance() {
        if(instance == null) {
            instance = new TestDb2Map();
        }

        return instance;
    }
}
