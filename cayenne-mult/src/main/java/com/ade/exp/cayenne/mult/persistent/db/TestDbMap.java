package com.ade.exp.cayenne.mult.persistent.db;

import com.ade.exp.cayenne.mult.persistent.db.auto._TestDbMap;

public class TestDbMap extends _TestDbMap {

    private static TestDbMap instance;

    private TestDbMap() {}

    public static TestDbMap getInstance() {
        if(instance == null) {
            instance = new TestDbMap();
        }

        return instance;
    }
}
