package com.juraj.hdbsWebAPI.services;

import com.juraj.hdbs.HeterogeneousDatabaseSystem;
import com.juraj.hdbs.utils.DBVendor;
import com.juraj.hdbs.utils.results.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Juraj on 19.4.2018..
 */
@Service
public class HDBSService {

    private volatile HeterogeneousDatabaseSystem hdbs;


    public HDBSService(@Autowired @Value("${metadata.db.url}") String metadataDbUrl){
        try {
            hdbs = new HeterogeneousDatabaseSystem(metadataDbUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ActionResult executeGlobalQuery(String queryText){
        return hdbs.executeGlobalQuery(queryText);
    }

    public String getGlobalSchema(){
        return  hdbs.getGlobalSchemaXML();
    }

    public ActionResult addConnection(String serverUrl, String dbName, String username, String password, DBVendor dbVendor){
        return hdbs.addConnectionToPool(serverUrl, dbName, username, password, dbVendor);
    }

    public ActionResult removeConnection(String dbName){
        return hdbs.removeConnectionFromPool(dbName);
    }

    public ActionResult addGlobalRelationship(String primaryKeyColumnId, String foreignKeyColumnId){
        return hdbs.addGlobalRelationship(primaryKeyColumnId, foreignKeyColumnId);
    }

    public ActionResult removeGlobalRelationship(String primaryKeyColumnId, String foreignKeyColumnId){
        return hdbs.removeGlobalRelationship(primaryKeyColumnId, foreignKeyColumnId);
    }

    public Set<String> getConnectedDbNames(){
        return hdbs.getConnectedDbNames();
    }

    public ActionResult checkGlobalRelationshipConsistency(String primaryKeyColumnId, String foreignKeyColumnId){
        return hdbs.checkGlobalRelationshipFkConsistency(primaryKeyColumnId, foreignKeyColumnId);
    }


}
