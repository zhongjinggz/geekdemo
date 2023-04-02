package demo.unjuanable.application.orgmng.orgservice;

import demo.unjuanable.domain.common.exception.BusinessException;
import demo.unjuanable.domain.orgmng.org.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrgService {
    private final OrgBuilderFactory orgBuilderFactory;
    private final OrgRepository orgRepository;
    private final OrgHandler orgHandler;

    @Autowired
    public OrgService(OrgBuilderFactory orgBuilderFactory
            , OrgHandler orgHandler
            , OrgRepository orgRepository) {
        this.orgBuilderFactory = orgBuilderFactory;
        this.orgHandler = orgHandler;
        this.orgRepository = orgRepository;
    }

    @Transactional
    public OrgResponse addOrg(CreateOrgRequest request, Long userId) {
        OrgBuilder builder = orgBuilderFactory.create();

        Org org = builder.tenant(request.getTenant())
                .orgType(request.getOrgType())
                .leader(request.getLeader())
                .superior(request.getSuperior())
                .name(request.getName())
                .createdBy(userId)
                .build();

        org = orgRepository.save(org);

        return buildOrgDto(org);
    }

    @Transactional
    public OrgResponse updateOrgBasic(Long id, UpdateOrgBasicRequest request, Long userId) {
        Org org = orgRepository.findById(request.getTenant(), id)
                .orElseThrow(() -> {
                    throw new BusinessException("要修改的组织(id =" + id + "  )不存在！");
                });

        orgHandler.updateBasic(org, request.getName() , request.getLeader(), userId);
        orgRepository.update(org);

        return buildOrgDto(org);
    }

    @Transactional
    public Long cancelOrg(Long id, Long tenant, Long userId) {
        Org org = orgRepository.findById(tenant, id)
                .orElseThrow(() -> {
                    throw new BusinessException("要取消的组织(id =" + id + "  )不存在！");
                });

        orgHandler.cancel(org, userId);
        orgRepository.update(org);

        return org.getId();
    }

    private static OrgResponse buildOrgDto(Org org) {
        OrgResponse response = new OrgResponse();
        response.setId(org.getId());
        response.setTenant(org.getTenant());
        response.setOrgType(org.getOrgType());
        response.setName(org.getName());
        response.setLeader(org.getLeader());
        response.setSuperior(org.getSuperior());
        response.setCreatedBy(org.getCreatedBy());
        response.setCreatedAt(org.getCreatedAt());
        response.setLastUpdatedBy(org.getLastUpdatedBy());
        response.setLastUpdatedAt(org.getLastUpdatedAt());
        return response;
    }

}
