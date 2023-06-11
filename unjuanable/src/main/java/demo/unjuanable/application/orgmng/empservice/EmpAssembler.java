package demo.unjuanable.application.orgmng.empservice;


import demo.unjuanable.domain.common.validator.OrgValidator;
import demo.unjuanable.domain.orgmng.emp.Emp;
import demo.unjuanable.domain.orgmng.emp.EmpHandler;
import demo.unjuanable.domain.orgmng.emp.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmpAssembler {
    EmpHandler handler;

    OrgValidator orgValidator;

    @Autowired
    public EmpAssembler(EmpHandler handler, OrgValidator orgValidator) {
        this.handler = handler;
        this.orgValidator = orgValidator;
    }

    Emp fromCreateRequest(CreateEmpRequest request, Long userId) {

        validateCreateRequest(request);

        String empNum = handler.generateNum();

        Emp result = new Emp(request.getTenantId(), userId);
        result.setNum(empNum)
                .setIdNum(request.getIdNum())
                .setDob(request.getDob())
                .setOrgId(request.getOrgId())
                .setGender(Gender.ofCode(request.getGenderCode()));

        return result;
    }

    void validateCreateRequest(CreateEmpRequest request) {
        orgValidator.orgShouldValid(
                request.getTenantId(), request.getOrgId());
    }

    EmpResponse toResponse(Emp emp) {
        EmpResponse result = new EmpResponse();

        result.setId(emp.getId())
                .setTenantId(emp.getTenantId())
                .setOrgId(emp.getOrgId())
                .setNum(emp.getNum())
                .setIdNum(emp.getIdNum())
                .setName(emp.getName())
                .setGender(emp.getGender().code())
                .setDob(emp.getDob())
                .setStatus(emp.getStatus().code());

        return result;
    }
}
