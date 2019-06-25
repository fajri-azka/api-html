package com.doku.restapi.services;

import com.doku.restapi.exception.DataNotFoundException;
import com.doku.restapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class TransactionServices {

    @Autowired
    private UserServices userServices;

    @Autowired
    private TransactionServices transactionServices;

    DataSahamRequestResponse returnSahamResponse;
    HashMap<String, DataSahamRequestResponse> sahamResponse;

    DataSahamRequest returnSahamRequest;
    HashMap<String, DataSahamRequest> sahamRequest;

    DataSahamTransactionStatus returnTransaction;
    HashMap<String, DataSahamTransactionStatus> transactionstatus;


    public DataSahamRequest createStock(DataSaham dataSaham) {
        returnSahamRequest = new DataSahamRequest();

        returnSahamRequest.setStockId(dataSaham.getStockId());
        String stockId = returnSahamRequest.getStockId();

        returnSahamRequest.setStockName(dataSaham.getStockName());
        returnSahamRequest.setStockPrice(dataSaham.getStockPrice());
        returnSahamRequest.setStockDailyReturn(dataSaham.getStockDailyReturn());

        if (sahamRequest == null){
            sahamRequest = new HashMap<>();
        }

        sahamRequest.put(stockId, returnSahamRequest);
        return returnSahamRequest;
    }

    public Collection <DataSahamRequest> getAllStock() { return sahamRequest.values(); }

    public DataSahamRequest getStock (String stockId){ return sahamRequest.get(stockId); }

    public  DataSahamRequestResponse createTransaction(DataSahamRequestResponse dataSahamRequestResponse) {
        returnSahamResponse = new DataSahamRequestResponse();

        String userTransactonId = dataSahamRequestResponse.getUserId();
        String stockTransactonId = dataSahamRequestResponse.getStockId();

        UserRequestResponse userRequestResponse = userServices.getUser(userTransactonId);
        DataSahamRequest dataSahamRequest = transactionServices.getStock(stockTransactonId);

        int stockUserRequestTemp = userRequestResponse.getStockRequest();
        int stockPriceTemp = dataSahamRequest.getStockPrice();

        returnSahamResponse.setUserId(userTransactonId);
        returnSahamResponse.setFullName(userRequestResponse.getFullName());
        returnSahamResponse.setStockId(dataSahamRequest.getStockId());
        returnSahamResponse.setStockName(dataSahamRequest.getStockName());
        returnSahamResponse.setStockSheetRequest(userRequestResponse.getStockRequest());
        returnSahamResponse.setStockPrice(dataSahamRequest.getStockPrice());
        returnSahamResponse.setStockPriceTotal(stockPriceTemp * stockUserRequestTemp);

        if (sahamResponse == null){
            sahamResponse = new HashMap<>();
        }

        sahamResponse.put(userTransactonId, returnSahamResponse);
        return returnSahamResponse;

    }

    public DataSahamTransactionStatus updateTransaction(DataSahamRequestResponse dataSahamRequestResponse) {
        returnTransaction = new DataSahamTransactionStatus();
        String userID = returnSahamResponse.getUserId();

        returnTransaction.setUserId(userID);
        String usertransactionid = returnTransaction.getUserId();

        UserRequestResponse userRequestResponse = userServices.getUser(userID);
        String stockTransactionId = dataSahamRequestResponse.getStockId();

        transactionServices.getStock(stockTransactionId);

        int currentMoneyTemp = userRequestResponse.getCurrentMoney();
        int stockPriceTotal = returnSahamResponse.getStockPriceTotal();

        if (currentMoneyTemp >= stockPriceTotal) {

            returnTransaction.setMessageTransactionStatus("Transaction Success");
            returnTransaction.setMoneyBalance(currentMoneyTemp - stockPriceTotal);
            returnTransaction.setReturnDaily(returnSahamResponse.getStockPriceTotal() * returnSahamRequest.getStockDailyReturn());
            returnTransaction.setReturnMonthly(returnTransaction.getReturnDaily() * 30);
            returnTransaction.setReturnYearly(returnTransaction.getReturnMonthly() * 12);

            if (transactionstatus == null) {
                transactionstatus = new HashMap<>();
            }
            transactionstatus.put(usertransactionid, returnTransaction);

            userRequestResponse.setCurrentMoney(returnTransaction.getMoneyBalance());
            return returnTransaction;

        } else {
            throw new DataNotFoundException("YOUR BALANCE IS NOT ENOUGH");
        }
    }

}
