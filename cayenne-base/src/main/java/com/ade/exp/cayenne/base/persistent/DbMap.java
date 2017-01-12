package com.ade.exp.cayenne.base.persistent;

import com.ade.exp.cayenne.base.persistent.auto._DbMap;

public class DbMap extends _DbMap {

    private static DbMap instance;

    private DbMap() {}

    public static DbMap getInstance() {
        if(instance == null) {
            instance = new DbMap();
        }

        return instance;
    }
}
