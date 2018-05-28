package com.juraj.hdbsWebAPI.controllers;

import com.juraj.hdbsWebAPI.models.QueryResponse;
import com.juraj.hdbsWebAPI.services.HDBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Juraj on 19.4.2018..
 */
@RestController
public class SchemaController {

    @Autowired
    private HDBSService hdbsService;

    @GetMapping("/schema")
    public ResponseEntity<QueryResponse> getGlobalSchema(){
        String schema = hdbsService.getGlobalSchema();

        return new ResponseEntity<QueryResponse>(new QueryResponse(schema, "Global schema in XML format under dataResult."), HttpStatus.OK);
    }
}
