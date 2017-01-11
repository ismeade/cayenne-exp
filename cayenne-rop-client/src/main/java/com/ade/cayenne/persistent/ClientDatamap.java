package com.ade.cayenne.persistent;

import com.ade.cayenne.persistent.auto._ClientDatamap;

public class ClientDatamap extends _ClientDatamap {

    private static ClientDatamap instance;

    private ClientDatamap() {}

    public static ClientDatamap getInstance() {
        if(instance == null) {
            instance = new ClientDatamap();
        }

        return instance;
    }
}
