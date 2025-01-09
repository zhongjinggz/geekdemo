package demo.unjuanable.common.framework.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// M - C 的集合管理器 （Collection Manager）
// C - 当前集合（需要被变更的集合）
// R - 请求集合（根据R变更C）
public abstract class CollectionUpdator<M, C, R> {

    public void update(M manager, Collection<C> current, Collection<R> request, Long userId) {
        // 创建 request 的副本
        Collection<R> requestCopy = new ArrayList<>(request);

        for (C currentItem : current) {
            boolean found = false;
            Iterator<R> requestIterator = requestCopy.iterator();
            while (requestIterator.hasNext()) {
                R requestItem = requestIterator.next();
                if (isSame(currentItem, requestItem)) {
                    updateItem(manager, requestItem, userId);
                    found = true;
                    requestIterator.remove(); // 删除 requestCopy 中的相应元素
                    break;
                }
            }
            if (!found) {
                removeItem(manager, currentItem);
            }
        }

        for (R requestItem : requestCopy) {
            addItem(manager, requestItem, userId);
        }
    }

    protected abstract boolean isSame(C currentItem, R requestItem);

    protected abstract void updateItem(M manager, R requestItem, Long userId);

    protected abstract void removeItem(M manager, C currentItem);
    
    protected abstract void addItem(M manager, R requestItem, Long userId);
}
