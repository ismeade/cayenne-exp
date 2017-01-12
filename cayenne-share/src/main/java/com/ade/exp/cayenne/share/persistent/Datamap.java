package com.ade.exp.cayenne.share.persistent;

import com.ade.exp.cayenne.share.persistent.auto._Datamap;

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
