package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.common.framework.application.CollectionUpdator;
import demo.unjuanable.domain.orgmng.emp.Emp;
import demo.unjuanable.domain.orgmng.emp.EmpHandler;
import demo.unjuanable.domain.orgmng.emp.EmpPost;
import org.springframework.stereotype.Component;

@Component
public class EmpPostUpdator extends CollectionUpdator<Emp, EmpPost, String> {
    private final EmpHandler handler;

    EmpPostUpdator(EmpHandler handler) {
        this.handler = handler;
    }

    @Override
    protected boolean isSame(EmpPost currentItem, String postCode) {
        return currentItem.getPostCode().equals(postCode);
    }

    @Override
    protected void updateItem(Emp emp, String postCode, Long userId) {
        // EmpPost 类没有 update 方法，所以什么都不用做
    }

    @Override
    protected void removeItem(Emp emp, EmpPost currentItem) {
        handler.deleteEmpPost(emp, currentItem.getPostCode());
    }

    @Override
    protected void addItem(Emp emp, String postCode, Long userId) {
        handler.addEmpPost(emp, postCode, userId);
    }
}