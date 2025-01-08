package demo.unjuanable.adapter.driving.restful.orgmng;

import demo.unjuanable.application.orgmng.orgservice.OrgService;
import demo.unjuanable.application.orgmng.orgservice.dto.OrgResponse;
import demo.unjuanable.application.orgmng.orgservice.dto.CreateOrgRequest;
import demo.unjuanable.application.orgmng.orgservice.dto.UpdateOrgBasicRequest;
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
    public OrgResponse addOrg(@RequestParam("userid") Long userId
            , @RequestBody CreateOrgRequest request) {
        return orgService.addOrg(request, userId);
    }

    @PatchMapping("/api/organizations/{id}")
    public OrgResponse updateOrgBasic(@PathVariable Long id
            , @RequestParam("userid") Long userId
            , @RequestBody UpdateOrgBasicRequest request) {
        //Long user = acquireUserId();
        return orgService.updateOrgBasic(id, request, userId);
    }


    @PostMapping("/api/organizations/{id}/cancel")
    public Long cancelOrg(@PathVariable Long id
            , @RequestParam("userid") Long userId
            , @RequestParam Long tenant) {
        //Long user = acquireUserId();
        return orgService.cancelOrg(tenant, id, userId);
    }

    private Long acquireUserId() {
        //TODO: use JWT
        return 1L;
    }
}
