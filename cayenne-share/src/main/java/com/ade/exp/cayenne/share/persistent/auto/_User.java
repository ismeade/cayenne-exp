package com.ade.exp.cayenne.share.persistent.auto;

import org.apache.cayenne.CayenneDataObject;

/**
 * Class _User was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _User extends CayenneDataObject {

    public static final String USER_ADDRESS_PROPERTY = "userAddress";
    public static final String USER_NAME_PROPERTY = "userName";

    public static final String USER_ID_PK_COLUMN = "USER_ID";

    public void setUserAddress(String userAddress) {
        writeProperty(USER_ADDRESS_PROPERTY, userAddress);
    }
    public String getUserAddress() {
        return (String)readProperty(USER_ADDRESS_PROPERTY);
    }

    public void setUserName(String userName) {
        writeProperty(USER_NAME_PROPERTY, userName);
    }
    public String getUserName() {
        return (String)readProperty(USER_NAME_PROPERTY);
    }

}
