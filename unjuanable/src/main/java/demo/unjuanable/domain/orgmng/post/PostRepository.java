package demo.unjuanable.domain.orgmng.post;

import demo.unjuanable.domain.orgmng.org.Org;
import demo.unjuanable.domain.orgmng.org.OrgStatus;

import java.util.Optional;

public interface PostRepository {
    Optional<Org> findByIdAndStatus(Long tenantId, Long id, OrgStatus status);

    int countBySuperiorAndName(Long tenantId, Long superiorId, String name);

    Org save(Org org);

    boolean existsBySuperiorAndName(Long tenant, Long superior, String name);
}
