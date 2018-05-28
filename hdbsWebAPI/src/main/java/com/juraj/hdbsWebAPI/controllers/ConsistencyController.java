package com.juraj.hdbsWebAPI.controllers;

import com.juraj.hdbs.utils.results.ActionResult;
import com.juraj.hdbs.utils.results.ActionResultType;
import com.juraj.hdbsWebAPI.models.QueryResponse;
import com.juraj.hdbsWebAPI.services.HDBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Juraj on 19.4.2018..
 */
@RestController
public class ConsistencyController {

    @Autowired
    private HDBSService hdbsService;


    @GetMapping("/consistency")
    public ResponseEntity<QueryResponse> isRelationshipConsistent(@RequestParam("pk") String primaryKeyId, @RequestParam("fk") String foreignKeyId){
        ActionResult actionResult = hdbsService.checkGlobalRelationshipConsistency(primaryKeyId, foreignKeyId);
        QueryResponse queryResponse;
        if (actionResult.getActionResultType() == ActionResultType.SUCCESS){
            queryResponse = new QueryResponse("true", actionResult.toString());
        } else {
            queryResponse = new QueryResponse("false", actionResult.toString());
        }

        return new ResponseEntity<QueryResponse>(queryResponse, HttpStatus.OK);
    }

}
