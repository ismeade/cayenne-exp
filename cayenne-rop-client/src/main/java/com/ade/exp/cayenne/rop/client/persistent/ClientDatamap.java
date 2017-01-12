package com.ade.exp.cayenne.rop.client.persistent;

import com.ade.exp.cayenne.rop.client.persistent.auto._ClientDatamap;

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
