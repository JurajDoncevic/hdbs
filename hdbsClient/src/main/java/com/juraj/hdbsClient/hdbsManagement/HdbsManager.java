package com.juraj.hdbsClient.hdbsManagement;

import com.juraj.hdbsClient.metamodel.GlobalRelationship;
import com.juraj.hdbsClient.utils.*;
import com.juraj.hdbsClient.utils.customHttpEntities.HttpDeleteWithEntity;
import com.juraj.hdbsClient.utils.customHttpEntities.HttpGetWithEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Juraj on 24.4.2018..
 */
public class HdbsManager {

    private String hdbsUrl;
    private CloseableHttpClient httpClient;

    public HdbsManager(String hdbsUrl) {
        this.hdbsUrl = "http://" + hdbsUrl;
        this.httpClient = null;
    }

    private void openClient(){
        httpClient = HttpClients.createDefault();
    }

    private void closeClient(){
        if (httpClient != null){
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        httpClient = null;
    }

    public boolean contactServer(){
        openClient();
        boolean success = false;

        HttpGet httpGet = new HttpGet(hdbsUrl + "/greeting");

        try {

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream instream = httpEntity.getContent();

            String result = new BufferedReader(new InputStreamReader(instream))
                    .lines().collect(Collectors.joining("\n"));

            if (result.equals("HELLO")){
                success = true;
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

        closeClient();

        return success;
    }

    public List<String> getConnectedDatabases(){
        openClient();

        List<String> resultList = _getConnectedDatabases();
        closeClient();
        return resultList;
    }

    public String getGlobalSchema(){
        openClient();

        String globalSchema = _getGlobalSchema();

        closeClient();
        return globalSchema;
    }

    public List<GlobalRelationship> getGlobalRelationships(){
        openClient();
        List<GlobalRelationship> globalRelationships = _getGlobalRelationships();
        closeClient();
        return globalRelationships;
    }

    public ActionResult removeGlobalRelationship(GlobalRelationship globalRelationship){

        openClient();
        ActionResult actionResult = _removeGlobalRelationship(globalRelationship);
        closeClient();
        return actionResult;
    }

    public ActionResult addGlobalRelationship(GlobalRelationship globalRelationship){
        openClient();
        ActionResult actionResult = _addGlobalRelationship(globalRelationship);
        closeClient();
        return actionResult;
    }

    public ActionResult sendQuery(String queryText){
        openClient();

        ActionResult actionResult = _sendQuery(queryText);

        closeClient();
        return actionResult;
    }

    public boolean addConnection(String serverUrl, String dbName, String userName, String password, DbVendor dbVendor){
        openClient();
        boolean result = _addConnection(serverUrl, dbName, userName, password, dbVendor);
        closeClient();

        return result;
    }

    public ActionResult removeConnection(String dbName){
        openClient();
        ActionResult actionResult = _removeConnection(dbName);
        closeClient();
        return actionResult;
    }

    public ActionResult isRelationshipConsistent(GlobalRelationship globalRelationship){
        openClient();
        ActionResult actionResult = _isRelationshipConsistent(globalRelationship);
        closeClient();
        return actionResult;
    }

    private List<String> _getConnectedDatabases(){

        HttpGet httpGet = new HttpGet(hdbsUrl + "/connection");
        List<String> resultList = new ArrayList<>();
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            InputStream instream = httpEntity.getContent();

            String result = new BufferedReader(new InputStreamReader(instream))
                    .lines().collect(Collectors.joining("\n"));
            result = String.format("{result:%s}", result);
            JSONObject jsonObject = new JSONObject(result);
            resultList = jsonObject.getJSONArray("result").toList().stream().map(x -> x.toString()).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    private String _getGlobalSchema(){
        String globalSchema = "";
        HttpGet httpGet = new HttpGet(hdbsUrl + "/schema");
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            InputStream instream = httpEntity.getContent();

            String result = new BufferedReader(new InputStreamReader(instream))
                    .lines().collect(Collectors.joining("\n"));

            JSONObject jsonObject = new JSONObject(result);
            globalSchema = jsonObject.getString("dataResult");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return globalSchema;
    }

    private List<GlobalRelationship> _getGlobalRelationships(){
        String globalSchema = "";
        HttpGet httpGet = new HttpGet(hdbsUrl + "/schema");
        List<GlobalRelationship> globalRelationships = new ArrayList<>();
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            InputStream instream = httpEntity.getContent();

            String result = new BufferedReader(new InputStreamReader(instream))
                    .lines().collect(Collectors.joining("\n"));

            JSONObject jsonObject = new JSONObject(result);
            globalSchema = jsonObject.getString("dataResult");


            SAXBuilder sb = new SAXBuilder();

            Document doc = null;
            try {
                doc = sb.build(new ByteArrayInputStream(globalSchema.getBytes(StandardCharsets.UTF_8)));
            } catch (JDOMException e) {
                e.printStackTrace();
            }

            Element root = doc.getRootElement().getChild("globalRelationships");
            List<Element> globalRelationshipElements = doc.getRootElement().getChild("globalRelationships").getChildren("globalRelationship");
            if (globalRelationshipElements != null)
                for (Element grElement : globalRelationshipElements){
                    String pkId = grElement.getAttributeValue("pkColumnId");
                    String fkId = grElement.getAttributeValue("fkColumnId");
                    GlobalRelationship globalRelationship = new GlobalRelationship(pkId, fkId);
                    globalRelationships.add(globalRelationship);
                }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return globalRelationships;
    }

    private ActionResult _removeGlobalRelationship(GlobalRelationship globalRelationship){

        JSONObject reqJsonObject = new JSONObject();
        reqJsonObject.put("primaryKeyId", globalRelationship.getPrimaryKeyId());
        reqJsonObject.put("foreignKeyId", globalRelationship.getForeignKeyId());
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(reqJsonObject.toString());
            stringEntity.setContentType("application/json");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpDeleteWithEntity httpDelete = new HttpDeleteWithEntity(hdbsUrl + "/relationship");
        httpDelete.setEntity(stringEntity);

        try {
            HttpResponse httpResponse = httpClient.execute(httpDelete);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return new ActionResult(ActionResultType.SUCCESS, "Relationship removed successfully.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ActionResult(ActionResultType.FAILURE, "Failed to remove relationship.");
    }

    private ActionResult _addGlobalRelationship(GlobalRelationship globalRelationship){

        JSONObject reqJsonObject = new JSONObject();
        reqJsonObject.put("primaryKeyId", globalRelationship.getPrimaryKeyId());
        reqJsonObject.put("foreignKeyId", globalRelationship.getForeignKeyId());
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(reqJsonObject.toString());
            stringEntity.setContentType("application/json");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpPost httpPost = new HttpPost(hdbsUrl + "/relationship");
        httpPost.setEntity(stringEntity);

        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {

                HttpEntity responseEntity = httpResponse.getEntity();

                InputStream instream = responseEntity.getContent();

                String result = new BufferedReader(new InputStreamReader(instream))
                        .lines().collect(Collectors.joining("\n"));
                JSONObject jsonObject = new JSONObject(result);
                String message = jsonObject.getString("message");

                return new ActionResult(ActionResultType.SUCCESS, message);
            } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                HttpEntity responseEntity = httpResponse.getEntity();

                InputStream instream = responseEntity.getContent();

                String result = new BufferedReader(new InputStreamReader(instream))
                        .lines().collect(Collectors.joining("\n"));
                JSONObject jsonObject = new JSONObject(result);
                String message = jsonObject.getString("message");

                return new ActionResult(ActionResultType.FAILURE, message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ActionResult(ActionResultType.FAILURE, "Failed to remove relationship - unknown error.");
    }

    private ActionResult _sendQuery(String queryText){
        queryText = queryText.replaceAll("\n", " ");

        HttpGetWithEntity httpGet = new HttpGetWithEntity(hdbsUrl + "/query");
        JSONObject reqJsonObject = new JSONObject();
        reqJsonObject.put("query", queryText);
        StringEntity getHttpEntity = null;
        try {
            getHttpEntity = new StringEntity(reqJsonObject.toString());
            getHttpEntity.setContentType("application/json");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpGet.setEntity(getHttpEntity);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {

                String resultCSV = "";
                HttpEntity httpEntity = httpResponse.getEntity();

                InputStream instream = httpEntity.getContent();

                String result = new BufferedReader(new InputStreamReader(instream))
                        .lines().collect(Collectors.joining("\n"));
                JSONObject jsonObject = new JSONObject(result);
                resultCSV = jsonObject.getString("dataResult");

                return new ActionResult(ActionResultType.SUCCESS, "Results fetched successfully.", resultCSV);
            }
            else {
                HttpEntity httpEntity = httpResponse.getEntity();

                InputStream instream = httpEntity.getContent();

                String result = new BufferedReader(new InputStreamReader(instream))
                        .lines().collect(Collectors.joining("\n"));
                JSONObject jsonObject = new JSONObject(result);
                String message = jsonObject.getString("message");

                return new ActionResult(ActionResultType.FAILURE, message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ActionResult(ActionResultType.FAILURE, "Application error.");
    }

    private boolean _addConnection(String serverUrl, String dbName, String userName, String password, DbVendor dbVendor){

        HttpPost httpPost = new HttpPost(hdbsUrl + "/connection");
        JSONObject reqJsonObject = new JSONObject();
        reqJsonObject.put("server_url", serverUrl);
        reqJsonObject.put("db_name", dbName);
        reqJsonObject.put("user_name", userName);
        reqJsonObject.put("password", password);
        reqJsonObject.put("db_vendor", dbVendor.toString());
        StringEntity reqHttpEntity = null;
        try {
            reqHttpEntity = new StringEntity(reqJsonObject.toString());
            reqHttpEntity.setContentType("application/json");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpPost.setEntity(reqHttpEntity);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ActionResult _removeConnection(String dbName){
        HttpDelete httpDelete = new HttpDelete(hdbsUrl + "/connection/" + dbName);

        try {
            HttpResponse httpResponse = httpClient.execute(httpDelete);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                return new ActionResult(ActionResultType.SUCCESS, "DB deleted.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ActionResult(ActionResultType.FAILURE, "Failed to delete database.");
    }

    private ActionResult _isRelationshipConsistent(GlobalRelationship globalRelationship){
        URIBuilder builder = null;
        HttpGet httpGet = null;
        try {
            builder = new URIBuilder(hdbsUrl + "/consistency");
            builder.setParameter("pk", globalRelationship.getPrimaryKeyId());
            builder.setParameter("fk", globalRelationship.getForeignKeyId());
            httpGet = new HttpGet(builder.build());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream instream = httpEntity.getContent();

                String result = new BufferedReader(new InputStreamReader(instream))
                        .lines().collect(Collectors.joining("\n"));
                JSONObject jsonObject = new JSONObject(result);
                String message = jsonObject.getString("message");
                String dataResult = jsonObject.getString("dataResult");

                return new ActionResult(ActionResultType.SUCCESS, message, dataResult);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        return new ActionResult(ActionResultType.FAILURE, String.format("Error getting data about the consistency of:%s-%s.", globalRelationship.getPrimaryKeyId(), globalRelationship.getForeignKeyId()));
    }
}
