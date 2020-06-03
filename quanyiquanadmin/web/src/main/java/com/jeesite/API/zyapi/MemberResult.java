package com.jeesite.API.zyapi;

/* 会员信息
 * @Author 马晓亮
 * @Date $ $
 * @Param $
 * @return $
 **/
public class MemberResult {

    private Long customerUid;  //会员在智廷系统的唯一标识
    private String categoryName; //会员所属分类名称
    private String number; //会员号
    private String name;    //会员姓名
    private Double point; //会员当前积分
    private Double balance;  //会员当前通用余额
    private String createdDate;  //创建会员的日期

    public Long getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(Long customerUid) {
        this.customerUid = customerUid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
