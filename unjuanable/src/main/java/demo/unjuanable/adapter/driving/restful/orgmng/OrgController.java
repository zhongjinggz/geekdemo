package demo.unjuanable.adapter.driving.restful.orgmng;

import demo.unjuanable.application.orgmng.orgservice.OrgService;
import demo.unjuanable.application.orgmng.orgservice.OrgResponse;
import demo.unjuanable.application.orgmng.orgservice.CreateOrgRequest;
import demo.unjuanable.application.orgmng.orgservice.UpdateOrgBasicRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrgController {
    private final OrgService orgService;
    @Autowired
    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }

    @PostMapping("/api/organizations")
    public OrgResponse addOrg(@RequestBody CreateOrgRequest request) {
        Long userId = acquireUserId();
        return orgService.addOrg(request, userId);
    }

    @PatchMapping("/api/organizations/{id}")
    public OrgResponse updateOrgBasic(@PathVariable Long id, @RequestBody UpdateOrgBasicRequest request) {
        Long user = acquireUserId();
        return orgService.updateOrgBasic(id, request, user);
    }


    @PostMapping("/api/organizations/{id}/cancel")
    public Long cancelOrg(@PathVariable Long id, @RequestParam Long tenant) {
        Long user = acquireUserId();
        return orgService.cancelOrg(tenant, id, user);
    }

    private Long acquireUserId() {
        //TODO: use JWT
        return 1L;
    }
}
