package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.common.framework.domain.Persistent;

public class Persister<T extends Persistent> {
    public T save(T theObject) {
        switch (theObject.getChangingStatus()) {
            case NEW:
                insert(theObject);
                saveSubsidiaries(theObject);
                break;
            case UPDATED:
                update(theObject);
                saveSubsidiaries(theObject);
                break;
            case DELETED:
                removeSubsidiaries(theObject);
                delete(theObject);
                break;
        }

        return theObject;
    }

    public void remove(T theObject) {
        removeSubsidiaries(theObject);
        delete(theObject);
    }

    protected void insert(T theObject) {
    }

    protected void update(T theObject) {

    }

    protected void delete(T theObject) {

    }

    protected void saveSubsidiaries(T theObject) {
    }

    protected void removeSubsidiaries(T theObject) {

    }
}
