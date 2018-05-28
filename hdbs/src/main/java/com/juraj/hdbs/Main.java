package com.juraj.hdbs;


import com.google.common.io.Resources;
import com.juraj.hdbs.utils.DBVendor;
import com.juraj.hdbs.utils.results.ActionResult;
import org.apache.commons.jexl3.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


public class Main {

    public static void main(String args[]){


        try {
            long time = System.nanoTime();
            HeterogeneousDatabaseSystem hdbs = new HeterogeneousDatabaseSystem("D:/Diplomski/metadata.db");
            time = System.nanoTime() - time;
            System.out.println("HDBS init time: " + String.valueOf(time / 1000000) + "ms");
            hdbs.getConnectedDbNames().forEach(System.out::println);

            String queryText;
            queryText = Resources.toString(Resources.getResource("query12.sql"), Charset.defaultCharset());
            time = System.nanoTime();
            ActionResult actionResult = hdbs.executeGlobalQuery(queryText);
            time = System.nanoTime() - time;
            System.out.println("Query exec time: " + String.valueOf(time / 1000000) + "ms");
            System.out.println(actionResult.toString());
            System.out.println(actionResult.getData());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void jexlTest(){
        Map<String, Boolean> map = new HashMap<>();

        map.put("1", true);
        map.put("2", false);
        map.put("3", false);

        JexlEngine jexlEngine = new JexlBuilder().create();

        String expression = "map.get(\"1\") && map.get(\"2\") || map.get(\"3\")";
        JexlExpression e = jexlEngine.createExpression(expression);

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("map", map);


        // Now evaluate the expression, getting the result
        Object o = e.evaluate(jc);

        System.out.println(o.toString());

        System.exit(0);
    }


}
