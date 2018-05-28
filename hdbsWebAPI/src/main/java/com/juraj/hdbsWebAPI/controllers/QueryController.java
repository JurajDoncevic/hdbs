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
 * Created by Juraj on 18.4.2018..
 */
@RestController
public class QueryController {


    @Autowired
    private HDBSService hdbsService;


    @GetMapping("/query")
    public ResponseEntity<QueryResponse> runQuery(@RequestBody(required = false) Map<String, Object> payload){

        QueryResponse queryResponse;
        ActionResult actionResult;

        if (payload != null && payload.containsKey("query")){

            actionResult = hdbsService.executeGlobalQuery(payload.get("query").toString());

            if (actionResult.getActionResultType() == ActionResultType.SUCCESS){
                queryResponse = new QueryResponse(actionResult.getData(), actionResult.getMessage());

                return new ResponseEntity<QueryResponse>(queryResponse, HttpStatus.OK);
            }
            else {
                queryResponse = new QueryResponse(actionResult.getData(), actionResult.getMessage());

                return new ResponseEntity<QueryResponse>(queryResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        else {
            queryResponse = new QueryResponse("", "Bad request body.");
            return new ResponseEntity<QueryResponse>(queryResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
