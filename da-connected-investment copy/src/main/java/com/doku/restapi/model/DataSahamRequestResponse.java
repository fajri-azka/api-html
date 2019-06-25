package com.doku.restapi.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter @Getter @ToString @NotNull
public class DataSahamRequestResponse {
    private String userId;
    private String fullName;
    private String stockId;
    private String stockName;
    private int stockSheetRequest;    // get from userRequestResponse
    private int stockPrice;           // stockPrice
    private int stockPriceTotal;      // stockPrice*stockSheet
}
