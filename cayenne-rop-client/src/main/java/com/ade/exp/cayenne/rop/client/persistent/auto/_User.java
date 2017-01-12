package com.ade.exp.cayenne.rop.client.persistent.auto;

import com.ade.exp.cayenne.rop.client.persistent.Company;
import org.apache.cayenne.PersistentObject;
import org.apache.cayenne.ValueHolder;
import org.apache.cayenne.util.PersistentObjectHolder;

/**
 * A generated persistent class mapped as "User" Cayenne entity. It is a good idea to
 * avoid changing this class manually, since it will be overwritten next time code is
 * regenerated. If you need to make any customizations, put them in a subclass.
 */
public abstract class _User extends PersistentObject {

    public static final String USER_ADDRESS_PROPERTY = "userAddress";
    public static final String USER_DESC_PROPERTY = "userDesc";
    public static final String USER_GENDER_PROPERTY = "userGender";
    public static final String USER_NAME_PROPERTY = "userName";
    public static final String COMPANY_PROPERTY = "company";

    protected String userAddress;
    protected String userDesc;
    protected Integer userGender;
    protected String userName;
    protected ValueHolder company;

    public String getUserAddress() {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "userAddress", false);
        }

        return userAddress;
    }
    public void setUserAddress(String userAddress) {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "userAddress", false);
        }

        Object oldValue = this.userAddress;
        // notify objectContext about simple property change
        if(objectContext != null) {
            objectContext.propertyChanged(this, "userAddress", oldValue, userAddress);
        }
        
        this.userAddress = userAddress;
    }

    public String getUserDesc() {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "userDesc", false);
        }

        return userDesc;
    }
    public void setUserDesc(String userDesc) {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "userDesc", false);
        }

        Object oldValue = this.userDesc;
        // notify objectContext about simple property change
        if(objectContext != null) {
            objectContext.propertyChanged(this, "userDesc", oldValue, userDesc);
        }
        
        this.userDesc = userDesc;
    }

    public Integer getUserGender() {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "userGender", false);
        }

        return userGender;
    }
    public void setUserGender(Integer userGender) {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "userGender", false);
        }

        Object oldValue = this.userGender;
        // notify objectContext about simple property change
        if(objectContext != null) {
            objectContext.propertyChanged(this, "userGender", oldValue, userGender);
        }
        
        this.userGender = userGender;
    }

    public String getUserName() {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "userName", false);
        }

        return userName;
    }
    public void setUserName(String userName) {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "userName", false);
        }

        Object oldValue = this.userName;
        // notify objectContext about simple property change
        if(objectContext != null) {
            objectContext.propertyChanged(this, "userName", oldValue, userName);
        }
        
        this.userName = userName;
    }

    public Company getCompany() {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "company", true);
        } else if (this.company == null) {
        	this.company = new PersistentObjectHolder(this, "company");
		}

        return (Company) company.getValue();
    }
    public void setCompany(Company company) {
        if(objectContext != null) {
            objectContext.prepareForAccess(this, "company", true);
        } else if (this.company == null) {
        	this.company = new PersistentObjectHolder(this, "company");
		}

        // note how we notify ObjectContext of change BEFORE the object is actually
        // changed... this is needed to take a valid current snapshot
        Object oldValue = this.company.getValueDirectly();
        if (objectContext != null) {
        	objectContext.propertyChanged(this, "company", oldValue, company);
        }
        
        this.company.setValue(company);
    }

}
