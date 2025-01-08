package demo.unjuanable.common.framework.domain;

import static demo.unjuanable.common.framework.domain.ChangingStatus.*;

public class AbstractPersistent implements Persistent {
    protected ChangingStatus changingStatus = NEW;

    public ChangingStatus getChangingStatus() {
        return changingStatus;
    }

    public void toUpdate() {
        if (this.changingStatus == UNCHANGED) {
            this.changingStatus = UPDATED;
        }
    }

    public void toDelete() {
        this.changingStatus = DELETED;
    }
}
