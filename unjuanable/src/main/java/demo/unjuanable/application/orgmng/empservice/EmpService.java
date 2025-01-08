package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.application.orgmng.empservice.dto.CreateEmpRequest;
import demo.unjuanable.application.orgmng.empservice.dto.EmpResponse;
import demo.unjuanable.application.orgmng.empservice.dto.UpdateEmpRequest;
import demo.unjuanable.common.framework.exception.BusinessException;
import demo.unjuanable.domain.orgmng.emp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpService {
    private final EmpRepository empRepository;
    private final EmpUpdator updator;
    private final EmpBuilderFactory builderFactory;


    @Autowired
    public EmpService(EmpRepository empRepository
            , EmpUpdator updator
    , EmpBuilderFactory builderFactory
    ) {
        this.empRepository = empRepository;
        this.updator = updator;
        this.builderFactory = builderFactory;
    }

    @Transactional
    public EmpResponse addEmp(CreateEmpRequest request, Long userId) {
        Emp emp = builderFactory.builder().build(request, userId);

        empRepository.save(emp);
        return new EmpResponse(emp);
    }

    @Transactional
    public EmpResponse updateEmp(Long empId, UpdateEmpRequest request, Long userId) {
        Emp emp = empRepository.findById(request.getTenantId(), empId)
                .orElseThrow(() -> new BusinessException(
                        "Emp id(" + empId + ") 不正确！"));

        updator.update(emp, request, userId);

        empRepository.save(emp);
        return new EmpResponse(emp);
    }

}
