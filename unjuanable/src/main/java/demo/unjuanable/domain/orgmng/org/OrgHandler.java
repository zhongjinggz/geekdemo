package demo.unjuanable.domain.orgmng.org;

import demo.unjuanable.domain.common.validator.CommonValidator;
import demo.unjuanable.domain.orgmng.org.validator.CancelOrgValidator;
import demo.unjuanable.domain.orgmng.org.validator.OrgLeaderValidator;
import demo.unjuanable.domain.orgmng.org.validator.OrgNameValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrgHandler {
    private final CommonValidator commonValidator;
    private final OrgNameValidator nameValidator;
    private final OrgLeaderValidator leaderValidator;
    private CancelOrgValidator cancelValidator;

    public OrgHandler(CommonValidator commonValidator
            , OrgNameValidator nameValidator
            , OrgLeaderValidator leaderValidator
            , CancelOrgValidator cancelValidator) {

        this.commonValidator = commonValidator;
        this.nameValidator = nameValidator;
        this.leaderValidator = leaderValidator;
        this.cancelValidator = cancelValidator;
    }

    public void updateBasic(Org org, String newName, Long newLeader, Long userId) {
        updateName(org, newName);
        updateLeader(org, newLeader);
        updateAuditInfo(org, userId);
    }

    public void cancel(Org org, Long userId) {
        cancelValidator.OrgToBeCancelledShouldNotHasEmp(org.getTenant(), org.getId());
        cancelValidator.OnlyEffectiveOrgCanBeCancelled(org);
        org.cancel();
        updateAuditInfo(org, userId);
    }

    private void updateLeader(Org org, Long newLeader) {
        if (newLeader != null && !newLeader.equals(org.getLeader())) {
            leaderValidator.leaderShouldBeEffective(org.getTenant(), newLeader);
            org.setLeader(newLeader);
        }
    }

    private void updateName(Org org, String newName) {
        if (newName != null && !newName.equals(org.getName())) {
            nameValidator.orgNameShouldNotEmpty(newName);
            nameValidator.nameShouldNotDuplicatedInSameSuperior(org.getTenant(), org.getSuperior(), newName);
            org.setName(newName);
        }
    }

    private void updateAuditInfo(Org org, Long userId) {
        org.setLastUpdatedBy(userId);
        org.setLastUpdatedAt(LocalDateTime.now());
    }
}
