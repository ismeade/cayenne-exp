package com.ade.exp.cayenne.rop.client.persistent.auto;

import java.util.List;

import com.ade.exp.cayenne.rop.client.persistent.User;
import org.apache.cayenne.PersistentObject;
import org.apache.cayenne.util.PersistentObjectList;

/**
 * A generated persistent class mapped as "Company" Cayenne entity. It is a good idea to
 * avoid changing this class manually, since it will be overwritten next time code is
 * regenerated. If you need to make any customizations, put them in a subclass.
 */
public abstract class _Company extends PersistentObject {

    public static final String COMPANY_ADDRESS_PROPERTY = "companyAddress";
    public static final String COMPANY_NAME_PROPERTY = "companyName";
    public static final String USER_PROPERTY = "user";

    protected String companyAddress;
    protected String companyName;
    protected List<User> user;

    public String getCompanyAddress() {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "companyAddress", false);
        }

        return companyAddress;
    }
    public void setCompanyAddress(String companyAddress) {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "companyAddress", false);
        }

        Object oldValue = this.companyAddress;
        // notify objectContext about simple property change
        if(objectContext != null) {
            objectContext.propertyChanged(this, "companyAddress", oldValue, companyAddress);
        }
        
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "companyName", false);
        }

        return companyName;
    }
    public void setCompanyName(String companyName) {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "companyName", false);
        }

        Object oldValue = this.companyName;
        // notify objectContext about simple property change
        if(objectContext != null) {
            objectContext.propertyChanged(this, "companyName", oldValue, companyName);
        }
        
        this.companyName = companyName;
    }

    public List<User> getUser() {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "user", true);
        } else if (this.user == null) {
        	this.user = new PersistentObjectList(this, "user");
		}

        return user;
    }
    public void addToUser(User object) {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "user", true);
        } else if (this.user == null) {
        	this.user = new PersistentObjectList(this, "user");
		}

        this.user.add(object);
    }
    public void removeFromUser(User object) {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "user", true);
        } else if (this.user == null) {
        	this.user = new PersistentObjectList(this, "user");
		}

        this.user.remove(object);
    }

}
