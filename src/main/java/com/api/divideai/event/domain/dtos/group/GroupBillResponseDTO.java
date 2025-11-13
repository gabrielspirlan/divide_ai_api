package com.api.divideai.event.domain.dtos.group;

import java.util.List;

public class GroupBillResponseDTO {

    private String groupId;
    private String groupName;
    private Double totalExpenses;
    private Double totalIndividualExpenses;
    private Double totalSharedExpenses;
    private List<UserBillDTO> userBills;

    public GroupBillResponseDTO() {
    }

    public GroupBillResponseDTO(String groupId, String groupName, Double totalExpenses, 
                                Double totalIndividualExpenses, Double totalSharedExpenses, 
                                List<UserBillDTO> userBills) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.totalExpenses = totalExpenses;
        this.totalIndividualExpenses = totalIndividualExpenses;
        this.totalSharedExpenses = totalSharedExpenses;
        this.userBills = userBills;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getTotalIndividualExpenses() {
        return totalIndividualExpenses;
    }

    public void setTotalIndividualExpenses(Double totalIndividualExpenses) {
        this.totalIndividualExpenses = totalIndividualExpenses;
    }

    public Double getTotalSharedExpenses() {
        return totalSharedExpenses;
    }

    public void setTotalSharedExpenses(Double totalSharedExpenses) {
        this.totalSharedExpenses = totalSharedExpenses;
    }

    public List<UserBillDTO> getUserBills() {
        return userBills;
    }

    public void setUserBills(List<UserBillDTO> userBills) {
        this.userBills = userBills;
    }
}

