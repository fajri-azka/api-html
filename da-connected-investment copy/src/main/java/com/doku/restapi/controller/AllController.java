package com.doku.restapi.controller;

import com.doku.restapi.model.*;
import com.doku.restapi.services.TransactionServices;
import com.doku.restapi.services.UserServices;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Api("User Management API Documentation")
@Controller
@Slf4j
public class AllController {

    @Autowired
    UserServices userServices;

    @Autowired
    TransactionServices transactionServices;

    @GetMapping("/welcome")
    public String showWelcomePageForm(){
        return "homePage";
    }

    @GetMapping("/home-page")
    public String showHomePageForm(){
        return "homePageUpdate";
    }


    // USER //


    @GetMapping("/create-user")             // CREATE USER
    public String showCreateForm(UserRequest userRequest, Model model){
        model.addAttribute("userRequest", userRequest);
        return "User/createUser";
    }

    @PostMapping("/create")
    public String createUserForm(@Validated @ModelAttribute("userRequest") UserRequest userRequest, BindingResult result, Model model){
        if(result.hasErrors()){
            return "User/createUser";
        }

        userServices.createUser(userRequest);
        model.addAttribute("userRes", userServices.getAllUser());
        log.info(userServices.getAllUser().toString());
        return "User/viewAllUser";
    }

    @GetMapping("/update-user/{userId}")    // UPDATE USER
    public String showUserByIdForm(@Valid @PathVariable("userId") String userId, Model model){
        UserRequestResponse requestResponse = userServices.getUser(userId);
        model.addAttribute("userRequest", requestResponse);
        return "User/editUser";
    }

    @PostMapping("/update/{userId}")
    public String updateUserForm(@Valid @PathVariable("userId") String userId, UserRequestResponse requestResponse, BindingResult result, Model model){
        if(result.hasErrors()){
            return "User/editUser";
        }

        userServices.updateUser(userId, requestResponse);
        model.addAttribute("userRes", userServices.getAllUser());
        log.info(userServices.getAllUser().toString());
        return "User/viewAllUser";
    }

    @GetMapping("/delete-user/{userId}")    // DELETE USER
    public String deleteUserByIdForm(@PathVariable("userId") String userId, Model model){
        userServices.deleteUser(userId);
        model.addAttribute("userRes", userServices.getAllUser());
        return "User/viewAllUser";
    }

    @GetMapping("/view-all-user")           // VIEW ALL USER
    public String showAllUserForm(Model model) {
        model.addAttribute("userRes", userServices.getAllUser());
        log.info(userServices.getAllUser().toString());
        return "User/viewAllUser";
    }


    // SAHAM //


    @GetMapping("/create-saham")            // CREATE SAHAM
    public String showCreateSahamForm(DataSaham dataSaham, Model model){
        model.addAttribute("dataSaham", dataSaham);
        return "Saham/createSaham";
    }

    @PostMapping("/creates")
    public String createSahamForm(@Valid DataSaham dataSaham, BindingResult result, Model model){
        if(result.hasErrors()){
            return "Saham/createSaham";
        }

        transactionServices.createStock(dataSaham);
        model.addAttribute("sahams", transactionServices.getAllStock());
        log.info(transactionServices.getAllStock().toString());
        return "Saham/viewAllSaham";
    }


    @GetMapping("/view-saham/{sahamid}")    // VIEW SAHAM
    public String showSahamByIdForm(@PathVariable("sahamid") String sahamid, Model model){
        DataSahamRequest dataSahamRequest = transactionServices.getStock(sahamid);
        model.addAttribute("dataSaham", dataSahamRequest);
        return "Saham/viewAllSaham";
    }


    @GetMapping("/view-all-saham")          // VIEW ALL SAHAM
    public String showAllsahamForm(@Valid DataSaham dataSaham, BindingResult result, Model model) {
        model.addAttribute("sahams", transactionServices.getAllStock());
        log.info(transactionServices.getAllStock().toString());
        return "Saham/viewAllSaham";
    }


    // TRANSACTION //

    @GetMapping("/create-transaction")      // CREATE TRANSACTION
    public String showCreateSahamForm(@Valid  DataSahamRequestResponse dataSahamRequestResponse, Model model){
        model.addAttribute("dataSahamRequestResponse", dataSahamRequestResponse);
        return "Transaction/Transaction-preinquiry";
    }

    @PostMapping("/createtransaction")
    public String createSahamForm(@Valid DataSahamRequestResponse dataSahamRequestResponse, BindingResult result, Model model){
        if(result.hasErrors()){
            return "Transaction/Transaction-preinquiry";
        }

        transactionServices.createTransaction(dataSahamRequestResponse);
        model.addAttribute("dataSahamRequestResponse", dataSahamRequestResponse);
        log.info(dataSahamRequestResponse.toString());
        return "Transaction/Transaction-inquiry";
    }


}
