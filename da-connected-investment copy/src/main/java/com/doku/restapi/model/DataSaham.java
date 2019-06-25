package com.doku.restapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter @NotNull
public class DataSaham {
    private String stockId;
    private String stockName;
    private int stockPrice;
    private double stockDailyReturn; //dalam %
}


