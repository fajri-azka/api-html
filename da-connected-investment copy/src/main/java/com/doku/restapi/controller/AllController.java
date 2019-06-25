package com.doku.restapi.controller;

import com.doku.restapi.model.DataSaham;
import com.doku.restapi.model.DataSahamRequest;
import com.doku.restapi.model.UserRequest;
import com.doku.restapi.model.UserRequestResponse;
import com.doku.restapi.services.TransactionServices;
import com.doku.restapi.services.UserServices;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/home")
    public String showHomePageForm(){
        return "homePage";
    }

    @GetMapping("/create-user")
    public String showCreateForm(@Valid UserRequest userRequest, Model model){
        model.addAttribute("userRequest", userRequest);
        return "createUser";
    }

    @PostMapping("/create")
    public String createUserForm(@Valid UserRequest userRequest, BindingResult result, Model model){
        if(result.hasErrors()){
            return "createUser";
        }

        userServices.createUser(userRequest);
        model.addAttribute("users", userServices.getAllUser());
        log.info(userServices.getAllUser().toString());
        return "viewAllUser";
    }

    @GetMapping("/update-user/{userid}")
    public String showUserByIdForm(@Valid @PathVariable("userid") String userid, Model model){
        UserRequestResponse RequestResponse = userServices.getUser(userid);
        model.addAttribute("userRequest", RequestResponse);
        return "editUser";
    }

    @PostMapping("/update/{userid}")
    public String updateUserForm(@Valid @PathVariable("userid") String userid, UserRequestResponse userRequestResponse, BindingResult result, Model model){
        if(result.hasErrors()){
            return "editUser";
        }

        userServices.updateUser(userid, userRequestResponse);
        model.addAttribute("userRequest", userServices.getAllUser());
        log.info(userServices.getAllUser().toString());
        return "viewAllUser";
    }


    @GetMapping("/delete-user/{userid}")
    public String deleteUserByIdForm(@PathVariable("userid") String userid, Model model){
        UserRequestResponse RequestResponse = userServices.deleteUser(userid);
        model.addAttribute("userRequest", RequestResponse);
        return "viewAllUser";
    }

    @GetMapping("/view-all-user")
    public String showAllUserForm(Model model) {
        model.addAttribute("users", userServices.getAllUser());
        log.info(userServices.getAllUser().toString());
        return "viewAllUser";
    }





    @GetMapping("/create-saham")
    public String showCreateSahamForm(@Valid DataSaham dataSaham, Model model){
        model.addAttribute("dataSaham", dataSaham);
        return "createSaham";
    }

    @PostMapping("/creates")
    public String createSahamForm(@Valid DataSaham dataSaham, BindingResult result, Model model){
        if(result.hasErrors()){
            return "createSaham";
        }

        transactionServices.createStock(dataSaham);
        model.addAttribute("sahams", transactionServices.getAllStock());
        log.info(transactionServices.getAllStock().toString());
        return "viewAllSaham";
    }

    @GetMapping("/view-saham/{sahamid}")
    public String showSahamByIdForm(@PathVariable("sahamid") String sahamid, Model model){
        DataSahamRequest dataSahamRequest = transactionServices.getStock(sahamid);
        model.addAttribute("userRequest", dataSahamRequest);
        return "editUser";
    }


    @GetMapping("/view-all-saham")
    public String showAllsahamForm(@Valid DataSaham dataSaham, BindingResult result, Model model) {
        model.addAttribute("sahams", transactionServices.getAllStock());
        log.info(transactionServices.getAllStock().toString());
        return "viewAllSaham";
    }


}
