package demo.unjuanable.common.framework.domain;

import static demo.unjuanable.common.framework.domain.ChangingStatus.*;

public class AbstractPersistent implements Persistent {
    protected ChangingStatus changingStatus = NEW;

    @Override
    public ChangingStatus getChangingStatus() {
        return changingStatus;
    }

    @Override
    public void toUpdate() {
        if (this.changingStatus == UNCHANGED) {
            this.changingStatus = UPDATED;
        }
    }

    @Override
    public void toDelete() {
        this.changingStatus = DELETED;
    }

    @Override
    public boolean isDeleted() {
        return getChangingStatus().equals(DELETED);
    }
}
