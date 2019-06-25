package com.doku.restapi.services;

import com.doku.restapi.model.UserRequest;
import com.doku.restapi.model.UserRequestResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class UserServices {

    UserRequestResponse returnValue;
    HashMap<String, UserRequestResponse> users;

    public UserRequestResponse createUser(UserRequest userDetails){
        returnValue = new UserRequestResponse();
        returnValue.setUserId(userDetails.getUserId());
        returnValue.setFullName(userDetails.getFullName());
        returnValue.setUserAddress(userDetails.getUserAddress());
        returnValue.setCurrentMoney(userDetails.getCurrentMoney());
        returnValue.setStockRequest(userDetails.getStockRequest());

        if (users == null){
            users = new HashMap<>();
        }

        String userId = Integer.toString(returnValue.getUserId());

        users.put(userId, returnValue);
        return returnValue;
    }

    public UserRequestResponse getUser (String userId){ return users.get(userId); }

    public UserRequestResponse deleteUser (String userId){ return users.remove(userId); }

    public Collection <UserRequestResponse> getAllUser (){ return users.values(); }

    public UserRequestResponse updateUser(String updateUserId, UserRequestResponse updateUserRequest){
        if (users.containsKey(updateUserId)){
            UserRequestResponse updateUserTemp = users.get(updateUserId);

            updateUserTemp.setFullName(updateUserRequest.getFullName());
            updateUserTemp.setUserAddress(updateUserRequest.getUserAddress());
            updateUserTemp.setCurrentMoney(updateUserRequest.getCurrentMoney());
            updateUserTemp.setStockRequest(updateUserRequest.getStockRequest());

            users.put(updateUserId, updateUserTemp);
        }
        return  users.get(updateUserId);
    }
}