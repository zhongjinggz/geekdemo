package demo.unjuanable.domain.orgmng.org;

import java.util.Optional;

public interface OrgRepository {
    Org save(Org org);

    Optional<Org> findById(Long tenantId, Long id);

    Optional<Org> findByIdAndStatus(Long tenantId, Long id, OrgStatus status);

    boolean existsBySuperiorAndName(Long tenant, Long superior, String name);
    int update(Org org);
}
