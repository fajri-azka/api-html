package com.doku.restapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Getter @Setter @NotNull
public class DataSaham {
    private String stockId;
    private String stockName;
    private Integer stockPrice;
    @DecimalMin(value = "0.00001", message = "Nominal minimal 0.00001")
    private Double stockDailyReturn; //dalam %
}


