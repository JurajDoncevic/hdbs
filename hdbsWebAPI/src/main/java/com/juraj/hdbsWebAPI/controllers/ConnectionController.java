package com.juraj.hdbsWebAPI.controllers;

import com.juraj.hdbs.utils.DBVendor;
import com.juraj.hdbs.utils.results.ActionResult;
import com.juraj.hdbs.utils.results.ActionResultType;
import com.juraj.hdbsWebAPI.models.QueryResponse;
import com.juraj.hdbsWebAPI.services.HDBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * Created by Juraj on 19.4.2018..
 */
@RestController
public class ConnectionController {

    @Autowired
    private HDBSService hdbsService;


    @PostMapping("/connection")
    public ResponseEntity<QueryResponse> addConnection(@RequestBody Map<String, Object> payload){
        if (payload.containsKey("user_name") && payload.containsKey("password")
                && payload.containsKey("server_url") && payload.containsKey("db_name")
                && payload.containsKey("db_vendor")){
            ActionResult actionResult;
            if (payload.get("db_vendor").toString().equals("POSTGRESQL")){
                actionResult = hdbsService.addConnection(payload.get("server_url").toString(), payload.get("db_name").toString(), payload.get("user_name").toString(), payload.get("password").toString(), DBVendor.POSTGRESQL);

            }
            else if (payload.get("db_vendor").toString().equals("MYSQL")){
                actionResult = hdbsService.addConnection(payload.get("server_url").toString(), payload.get("db_name").toString(), payload.get("user_name").toString(), payload.get("password").toString(), DBVendor.MYSQL);
            }
            else {
                return new ResponseEntity<QueryResponse>(new QueryResponse("", "db_vendor property not properly set"), HttpStatus.BAD_REQUEST);
            }

            if (actionResult.getActionResultType() == ActionResultType.SUCCESS){
                return new ResponseEntity<QueryResponse>(new QueryResponse("", actionResult.toString()), HttpStatus.OK);
            } else {
                return new ResponseEntity<QueryResponse>(new QueryResponse("", actionResult.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        else {
            return new ResponseEntity<QueryResponse>(new QueryResponse("", "Bad request body."), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/connection")
    public ResponseEntity<?> getConnections(){
        return new ResponseEntity<Set<String>>(hdbsService.getConnectedDbNames(), HttpStatus.OK);
    }

    @DeleteMapping("/connection/{dbName}")
    public ResponseEntity<QueryResponse> removeConnection(@PathVariable("dbName") String dbName){
        ActionResult actionResult = hdbsService.removeConnection(dbName);

        if (actionResult.getActionResultType() == ActionResultType.SUCCESS){
            return new ResponseEntity<QueryResponse>(new QueryResponse("", actionResult.getMessage()), HttpStatus.OK);
        } else {
            return new ResponseEntity<QueryResponse>(new QueryResponse("", actionResult.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
