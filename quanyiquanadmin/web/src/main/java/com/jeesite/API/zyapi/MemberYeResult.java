package com.jeesite.API.zyapi;

/*修改金额积分返回
 * @Author 马晓亮
 * @Date $ $
 * @Param $
 * @return $
 **/
public class MemberYeResult {

    private Long customerUid;  //会员在智廷系统的唯一标识
    private Double balanceBeforeUpdate;  //修改前会员余额
    private Double balanceAfterUpdate;   //修改后会员余额
    private Double balanceIncrement;   //余额变动量
    private Double pointBeforeUpdate;   //修改前会员积分
    private Double pointAfterUpdate;   //修改后会员积分
    private Double pointIncrement;   //会员积分变动量
    private String dataChangeTime;   //数据在调用方的变动时间
    private String updateCustomerTime;   //更新到银系统时间的时间

    public Long getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(Long customerUid) {
        this.customerUid = customerUid;
    }

    public Double getBalanceBeforeUpdate() {
        return balanceBeforeUpdate;
    }

    public void setBalanceBeforeUpdate(Double balanceBeforeUpdate) {
        this.balanceBeforeUpdate = balanceBeforeUpdate;
    }

    public Double getBalanceAfterUpdate() {
        return balanceAfterUpdate;
    }

    public void setBalanceAfterUpdate(Double balanceAfterUpdate) {
        this.balanceAfterUpdate = balanceAfterUpdate;
    }

    public Double getBalanceIncrement() {
        return balanceIncrement;
    }

    public void setBalanceIncrement(Double balanceIncrement) {
        this.balanceIncrement = balanceIncrement;
    }

    public Double getPointBeforeUpdate() {
        return pointBeforeUpdate;
    }

    public void setPointBeforeUpdate(Double pointBeforeUpdate) {
        this.pointBeforeUpdate = pointBeforeUpdate;
    }

    public Double getPointAfterUpdate() {
        return pointAfterUpdate;
    }

    public void setPointAfterUpdate(Double pointAfterUpdate) {
        this.pointAfterUpdate = pointAfterUpdate;
    }

    public Double getPointIncrement() {
        return pointIncrement;
    }

    public void setPointIncrement(Double pointIncrement) {
        this.pointIncrement = pointIncrement;
    }

    public String getDataChangeTime() {
        return dataChangeTime;
    }

    public void setDataChangeTime(String dataChangeTime) {
        this.dataChangeTime = dataChangeTime;
    }

    public String getUpdateCustomerTime() {
        return updateCustomerTime;
    }

    public void setUpdateCustomerTime(String updateCustomerTime) {
        this.updateCustomerTime = updateCustomerTime;
    }
}
