package com.juraj.hdbsWebAPI.controllers;

import com.juraj.hdbs.utils.results.ActionResult;
import com.juraj.hdbs.utils.results.ActionResultType;
import com.juraj.hdbsWebAPI.models.QueryResponse;
import com.juraj.hdbsWebAPI.services.HDBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Juraj on 19.4.2018..
 */
@RestController
public class RelationshipController {

    @Autowired
    private HDBSService hdbsService;

    @PostMapping("/relationship")
    public ResponseEntity<QueryResponse> addRelationship(@RequestBody(required = true) Map<String, Object> payload) {
        ActionResult actionResult;
        QueryResponse queryResponse;
        if (payload != null && payload.containsKey("primaryKeyId") && payload.containsKey("foreignKeyId")){
            actionResult = hdbsService.addGlobalRelationship(payload.get("primaryKeyId").toString(), payload.get("foreignKeyId").toString());

            if (actionResult.getActionResultType() == ActionResultType.SUCCESS){
                return new ResponseEntity<QueryResponse>(new QueryResponse("", actionResult.toString()), HttpStatus.OK);
            } else {
                return new ResponseEntity<QueryResponse>(new QueryResponse("", actionResult.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<QueryResponse>(new QueryResponse("", "Bad request body."), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/relationship")
    public ResponseEntity<QueryResponse> removeRelationship(@RequestBody(required = true) Map<String, Object> payload) {
        ActionResult actionResult;
        QueryResponse queryResponse;
        if (payload != null && payload.containsKey("primaryKeyId") && payload.containsKey("foreignKeyId")){
            actionResult = hdbsService.removeGlobalRelationship(payload.get("primaryKeyId").toString(), payload.get("foreignKeyId").toString());

            if (actionResult.getActionResultType() == ActionResultType.SUCCESS){
                return new ResponseEntity<QueryResponse>(new QueryResponse("", actionResult.toString()), HttpStatus.OK);
            } else {
                return new ResponseEntity<QueryResponse>(new QueryResponse("", actionResult.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<QueryResponse>(new QueryResponse("", "Bad request body."), HttpStatus.BAD_REQUEST);
        }

    }

}
