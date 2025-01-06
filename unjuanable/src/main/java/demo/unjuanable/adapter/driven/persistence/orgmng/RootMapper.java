package demo.unjuanable.adapter.driven.persistence.orgmng;

import demo.unjuanable.common.framework.domain.Persistent;

// 根节点的持久化器
public class RootMapper<T extends Persistent>
        extends BaseMapper<T, T> {
    public T save(T theObject) {
        return super.save(theObject, null);
    }
}
