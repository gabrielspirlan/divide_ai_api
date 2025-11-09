package com.api.divideai.event.domain.dtos.transaction;

public class UserTransactionTotalsResponseDTO {
    private Double totalExpenses;
    private Double individualExpenses;
    private Double sharedExpenses;

    public UserTransactionTotalsResponseDTO() {
    }

    public UserTransactionTotalsResponseDTO(Double totalExpenses, Double individualExpenses, Double sharedExpenses) {
        this.totalExpenses = totalExpenses;
        this.individualExpenses = individualExpenses;
        this.sharedExpenses = sharedExpenses;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
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
}

