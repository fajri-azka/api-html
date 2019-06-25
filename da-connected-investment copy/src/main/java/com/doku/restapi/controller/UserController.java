package com.doku.restapi.controller;

import com.doku.restapi.exception.DataNotFoundException;
import com.doku.restapi.model.UserRequest;
import com.doku.restapi.model.UserRequestResponse;
import com.doku.restapi.services.TransactionServices;
import com.doku.restapi.services.UserServices;
//import com.oracle.tools.packager.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;


@Api("User Management API Documentation")
@Controller
//@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    UserServices userServices;

    @Autowired
    TransactionServices transactionServices;

//    @GetMapping("/awal")
//    public String showCreateForm(@Valid UserRequest userRequest, Model model){
//        model.addAttribute("userRequest", userRequest);
//        return "createUser";
//    }
//
//    @PostMapping("/create")
//    public String createUserForm(@Valid UserRequest userRequest, BindingResult result, Model model){
//        if(result.hasErrors()){
//            return "createUser";
//        }
//
//        userServices.createUser(userRequest);
//        model.addAttribute("users", userServices.getAllUser());
//        log.info(userServices.getAllUser().toString());
//        return "homePage";
//    }
//
//    @GetMapping("/homePage")
//    public String showFormInput(@Valid UserRequest userRequest, BindingResult result, Model model) {
//        model.addAttribute("users", userServices.getAllUser());
//        log.info(userServices.getAllUser().toString());
//        return "homePage";
//    }













    @ApiOperation(value = "Create User ")        //ADD USER
    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity createUser(@Valid @RequestBody UserRequest userDetails){
        UserRequestResponse returnValue = userServices.createUser(userDetails);
        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @ApiOperation(value = "Search User by ID")  //SEARCH BY ID
    @GetMapping(value = "/getById/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity getUser(@PathVariable String userId){
        UserRequestResponse returnValue = userServices.getUser(userId);
        if (returnValue != null){
            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        } else {
            throw new DataNotFoundException("Get User Request : Data Not Found");
        }
    }

    @ApiOperation(value = "Delete User")        //DELETE USER
    @DeleteMapping(value = "/deleteById/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId){
        UserRequestResponse returnValue = userServices.deleteUser(userId);
        if (returnValue != null){
            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        } else {
            throw new DataNotFoundException("Delete Request : Data Not Found");
        }
    }

    @ApiOperation(value = "Shows All User")     //SHOW ALL USER
    @GetMapping(value = "/showUser")
    public ResponseEntity getAllUser(){
        Collection returnValue = userServices.getAllUser();
        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @ApiOperation(value = "Shows All User")     //UPDATE USER
    @PatchMapping(value = "/updateUser/{userId}")
    public ResponseEntity updateUser(@PathVariable String userId, @Valid @RequestBody UserRequestResponse updateUserRequest){

        UserRequestResponse returnValue = userServices.updateUser(userId, updateUserRequest);
        return new ResponseEntity<>(returnValue, HttpStatus.CREATED);
    }
}
