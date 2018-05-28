package com.juraj.hdbsWebAPI;

import com.juraj.hdbs.HeterogeneousDatabaseSystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juraj on 18.4.2018..
 */
@SpringBootApplication
public class WebStart {

    public static void main(String[] args) {

        if (args.length == 2){

            String port = args[0].trim();
            String metadataDbUrl = args[1].trim();

            SpringApplication application = new SpringApplication(WebStart.class);
            Map<String, Object> props = new HashMap<>();
            props.put("server.port", port);
            props.put("metadata.db.url", metadataDbUrl);
            application.setDefaultProperties(props);
            application.run(args);
        } else {
            System.out.println("Invalid command line arguments. Run with: <service_port> <metadata_db_path>");
            System.exit(-1);
        }

    }





}
