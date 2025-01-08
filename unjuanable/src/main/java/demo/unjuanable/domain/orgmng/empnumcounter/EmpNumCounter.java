package demo.unjuanable.domain.orgmng.empnumcounter;


import demo.unjuanable.common.framework.domain.AbstractPersistent;

public class EmpNumCounter extends AbstractPersistent {
    final private long tenantId;
    private int yearNum;
    private int maxEmpNum;

    public EmpNumCounter(long tenantId, int yearNum, int maxEmpNum) {
        this.tenantId = tenantId;
        this.yearNum = yearNum;
        this.maxEmpNum = maxEmpNum;
    }

    public long getTenantId() {
        return tenantId;
    }

    public int getYearNum() {
        return yearNum;
    }

    public int getMaxEmpNum() {
        return maxEmpNum;
    }

    public void setMaxEmpNum(int maxEmpNum) {
        this.maxEmpNum = maxEmpNum;
    }

}
