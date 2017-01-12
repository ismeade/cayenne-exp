package com.ade.exp.cayenne.rop.server.persistent;

import com.ade.exp.cayenne.rop.server.persistent.auto._Datamap;

public class Datamap extends _Datamap {

    private static Datamap instance;

    private Datamap() {}

    public static Datamap getInstance() {
        if(instance == null) {
            instance = new Datamap();
        }

        return instance;
    }
}
