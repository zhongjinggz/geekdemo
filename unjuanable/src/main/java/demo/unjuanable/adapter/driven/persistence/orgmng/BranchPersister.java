package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.common.framework.domain.Persistent;

// 分枝节点（Branch, 即非根节点）的持久化器
// B - 分枝节点类型
// P - 父节点类型
public class BranchPersister<B extends Persistent, P extends Persistent> {
    public B save(B theObject, P parent) {
        switch (theObject.getChangingStatus()) {
            case NEW:
                insert(theObject, parent);
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

    public void remove(B theObject) {
        removeSubsidiaries(theObject);
        delete(theObject);
    }

    protected void insert(B theObject, P parent) {
    }

    protected void update(B theObject) {

    }

    protected void delete(B theObject) {

    }

    protected void saveSubsidiaries(B theObject) {
    }

    protected void removeSubsidiaries(B theObject) {

    }
}
