package com.ade.exp.cayenne.mult.persistent.db2;

import com.ade.exp.cayenne.mult.persistent.db2.auto._Company;

public class Company extends _Company {

    @Override
    protected void onPostAdd() {
        System.out.println("onPostAdd!!!");
    }


}
