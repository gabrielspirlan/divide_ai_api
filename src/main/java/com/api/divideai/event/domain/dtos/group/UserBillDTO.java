package com.api.divideai.event.domain.dtos.group;

public class UserBillDTO {

    private String userId;
    private String userName;
    private Double individualExpenses;
    private Double sharedExpenses;
    private Double totalToPay;

    public UserBillDTO() {
    }

    public UserBillDTO(String userId, String userName, Double individualExpenses, Double sharedExpenses, Double totalToPay) {
        this.userId = userId;
        this.userName = userName;
        this.individualExpenses = individualExpenses;
        this.sharedExpenses = sharedExpenses;
        this.totalToPay = totalToPay;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getIndividualExpenses() {
        return individualExpenses;
    }

    public void setIndividualExpenses(Double individualExpenses) {
        this.individualExpenses = individualExpenses;
    }

    public Double getSharedExpenses() {
        return sharedExpenses;
    }

    public void setSharedExpenses(Double sharedExpenses) {
        this.sharedExpenses = sharedExpenses;
    }

    public Double getTotalToPay() {
        return totalToPay;
    }

    public void setTotalToPay(Double totalToPay) {
        this.totalToPay = totalToPay;
    }
}

