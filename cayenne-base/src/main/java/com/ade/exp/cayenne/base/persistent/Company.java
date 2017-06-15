package com.ade.exp.cayenne.base.persistent;

import com.ade.exp.cayenne.base.persistent.auto._Company;

public class Company extends _Company {

    @Override
    protected void onPostAdd() {
        System.out.println("callback onPostAdd:" + this);
    }

    @Override
    protected void onPreRemove() {
        System.out.println("callback onPreRemove:" + this);

    }

    @Override
    protected void onPreUpdate() {
        System.out.println("callback onPreUpdate:" + this);

    }

    @Override
    protected void onPostPersist() {
        System.out.println("callback onPostPersist:" + this);

    }

    @Override
    protected void onPostRemove() {
        System.out.println("callback onPostRemove:" + this);

    }

    @Override
    protected void onPostUpdate() {
        System.out.println("callback onPostUpdate:" + this);

    }

    @Override
    protected void onPostLoad() {
        System.out.println("callback onPostLoad:" + this);

    }

    @Override
    protected void onPrePersist() {
        System.out.println("callback onPrePersist:" + this);

    }
}
