package demo.unjuanable.domain.orgmng.org;

import demo.unjuanable.common.framework.domain.AuditableEntity;

import java.time.LocalDateTime;

public class Org extends AuditableEntity {
    private final Long tenantId;
    private Long id;
    private Long superiorId;
    private String orgTypeCode;
    private Long leaderId;
    private String name;
    private OrgStatus status;

    public Org(Long tenantId, String orgTypeCode, LocalDateTime createdAt, Long createdBy) {
        super(createdAt, createdBy);
        this.tenantId = tenantId;
        this.orgTypeCode = orgTypeCode;
        status = OrgStatus.EFFECTIVE;
    }

    private Org(Loader loader) {
        super(loader.createdAt, loader.createdBy);
        this.tenantId = loader.tenantId;
        this.id = loader.id;
        this.superiorId = loader.superiorId;
        this.orgTypeCode = loader.orgTypeCode;
        this.leaderId = loader.leaderId;
        this.name = loader.name;
        this.status = OrgStatus.ofCode(loader.statusCode);
        this.lastUpdatedAt = loader.lastUpdatedAt;
        this.lastUpdatedBy = loader.lastUpdatedBy;
    }

    public Long getId() {
        return id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Long getSuperiorId() {
        return superiorId;
    }

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public String getName() {
        return name;
    }

    public OrgStatus getStatus() {
        return status;
    }

    public boolean isEffective() {
        return status.equals(OrgStatus.EFFECTIVE);
    }

    public boolean isCanceled() {
        return status.equals(OrgStatus.CANCELLED);
    }

    public void setSuperiorId(Long superiorId) {
        this.superiorId = superiorId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(OrgStatus status) {
        this.status = status;
    }

    public void cancel() {
        this.status = OrgStatus.CANCELLED;
    }

    void setId(Long id) {
        this.id = id;
    }

    public static Loader loader() {
        return new Loader();
    }

    // Loader 本质上是 Builder, 这里只用于从数据库加载对象，所以取名 Loader
    public static class Loader {
        private Long tenantId;
        private Long id;
        private Long superiorId;
        private String orgTypeCode;
        private Long leaderId;
        private String name;
        private String statusCode;
        private LocalDateTime createdAt;
        private Long createdBy;
        private LocalDateTime lastUpdatedAt;
        private Long lastUpdatedBy;

        public Loader tenantId(Long tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Loader id(Long id) {
            this.id = id;
            return this;
        }

        public Loader superiorId(Long superiorId) {
            this.superiorId = superiorId;
            return this;
        }

        public Loader orgTypeCode(String orgTypeCode) {
            this.orgTypeCode = orgTypeCode;
            return this;
        }

        public Loader leaderId(Long leaderId) {
            this.leaderId = leaderId;
            return this;
        }

        public Loader name(String name) {
            this.name = name;
            return this;
        }

        public Loader statusCode(String statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Loader createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Loader createdBy(Long createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Loader lastUpdatedAt(LocalDateTime lastUpdatedAt) {
            this.lastUpdatedAt = lastUpdatedAt;
            return this;
        }

        public Loader lastUpdatedBy(Long lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
            return this;
        }

        public Org load() {
            return new Org(this);
        }
    }
}
