package demo.unjuanable.domain.orgmng.org.validator;

import demo.unjuanable.domain.common.exception.BusinessException;
import demo.unjuanable.domain.orgmng.emp.EmpRepository;
import demo.unjuanable.domain.orgmng.emp.EmpStatus;
import demo.unjuanable.domain.orgmng.org.Org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancelOrgValidator {

    private final EmpRepository empRepository;

    @Autowired
    public CancelOrgValidator(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    // 要被撤销的组织不能有下属员工
    public void shouldNotHasEmp(Long tenant, Long id) {
        if (empRepository.existsByIdAndStatus(tenant, id, EmpStatus.PROBATION, EmpStatus.REGULAR)) {
            throw new BusinessException("该组织中仍然有员工，不能撤销！");
        }
    }

    // 只有有效组织才能被撤销
    public void shouldEffective(Org org) {
        if (!org.isEffective()){
            throw new BusinessException("该组织不是有效状态，不能撤销！");
        }
    }
}
