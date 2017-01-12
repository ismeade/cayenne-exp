package com.ade.exp.cayenne.mult.persistent.db2.auto;

import com.ade.exp.cayenne.mult.persistent.db.User;
import org.apache.cayenne.CayenneDataObject;

import java.util.List;

/**
 * Class _Company was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Company extends CayenneDataObject {

    public static final String ADDRESS_PROPERTY = "address";
    public static final String NAME_PROPERTY = "name";
    public static final String USER_PROPERTY = "User";

    public static final String ID_PK_COLUMN = "id";

    public void setAddress(String address) {
        writeProperty(ADDRESS_PROPERTY, address);
    }
    public String getAddress() {
        return (String)readProperty(ADDRESS_PROPERTY);
    }

    public void setName(String name) {
        writeProperty(NAME_PROPERTY, name);
    }
    public String getName() {
        return (String)readProperty(NAME_PROPERTY);
    }

    public void addToUser(User obj) {
        addToManyTarget(USER_PROPERTY, obj, true);
    }
    public void removeFromUser(User obj) {
        removeToManyTarget(USER_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<User> getUser() {
        return (List<User>)readProperty(USER_PROPERTY);
    }


    protected abstract void onPostAdd();

}
