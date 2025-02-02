package demo.unjuanable.domain.orgmng.org;

import java.util.Optional;

public interface OrgRepository {
    Org save(Org org);

    Optional<Org> findById(Long tenantId, Long id);

    Optional<Org> findByIdAndStatus(Long tenantId, Long id, OrgStatus status);

    boolean existsBySuperiorIdAndName(Long tenant, Long superior, String name);

    boolean existsByIdAndStatus(Long tenantId, Long orgId, OrgStatus effective);
}
