package demo.unjuanable.common.framework.domain;

public interface Persistent {
    ChangingStatus getChangingStatus();
    void toUpdate();
    void toDelete();
}
