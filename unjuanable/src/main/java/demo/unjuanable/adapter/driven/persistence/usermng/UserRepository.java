package demo.unjuanable.adapter.driven.persistence.usermng;

import demo.unjuanable.domain.usermng.UserStatus;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public int countByIdAndStatus(long tenantId, long id, UserStatus status) {
        return 0;
    }

    public boolean existsByIdAndStatus(long tenant, long id, UserStatus status) {
        return countByIdAndStatus(tenant , id , status) > 0;
    }
}
