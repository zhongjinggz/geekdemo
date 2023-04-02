package demo.unjuanable.domain.orgmng.org.validator;

import demo.unjuanable.domain.common.exception.BusinessException;
import demo.unjuanable.domain.orgmng.emp.EmpStatus;
import demo.unjuanable.domain.orgmng.emp.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgLeaderValidator {
    private final EmpRepository empRepository;

    @Autowired
    OrgLeaderValidator(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    // 组织负责人可以空缺，如果有的话，的必须是一个在职员工（含试用期）
    public void leaderShouldBeEffective(Long tenant, Long leader) {
        if (leader != null
                && !empRepository.existsByIdAndStatus(tenant, leader
                , EmpStatus.REGULAR, EmpStatus.PROBATION)) {
            throw new BusinessException("组织负责人(id='" + leader + "')不是在职员工！");
        }
    }
}
