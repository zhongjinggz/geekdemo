package demo.unjuanable.application.orgmng.empservice;

import demo.unjuanable.domain.orgmng.emp.Emp;
import demo.unjuanable.domain.orgmng.emp.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpService {
    private final EmpRepository empRepository;
    private final EmpAssembler assembler;
//    private final EmpUpdator updator;


    @Autowired
    public EmpService(EmpRepository empRepository
            , EmpAssembler assembler
            ) {
        this.empRepository = empRepository;
        this.assembler = assembler;
//        this.updator = updator;
    }

    @Transactional
    public EmpResponse addEmp(CreateEmpRequest request, Long userId) {
        Emp emp = assembler.fromCreateRequest(request, userId);

        empRepository.save(emp);
        return assembler.toResponse(emp);
    }

//    @Transactional
//    public EmpResponse updateEmp(Long empId, UpdateEmpRequest request, User user) {
//        Emp emp = empRepository.findById(request.getTenantId(), empId)
//                .orElseThrow(() -> new BusinessException(
//                        "Emp id(" + empId + ") 不正确！"));
//
//        updator.update(emp, request, user);
//
//        if(!empRepository.save(emp)) {
//            throw new BusinessException("这个员工已将被其他人同时修改了，请重新修改！");
//        }
//        return assembler.toResponse(emp);
//    }

}
