package com.juraj.hdbs;


import com.juraj.hdbs.Utils.DBVendor;


public class Main {

    public static void main(String args[]){


        try {
            HeterogeneousDatabaseSystem hdbs = new HeterogeneousDatabaseSystem("<path_to_file>");

            hdbs.addConnectionToPool("localhost:3306", "mysql1", "root", "admin", DBVendor.MYSQL);
            hdbs.addConnectionToPool("localhost:3306", "mysql2", "root", "admin", DBVendor.MYSQL);
            hdbs.addConnectionToPool("localhost:5432", "postgres1", "postgres", "admin", DBVendor.POSTGRESQL);
            hdbs.addConnectionToPool("localhost:5432", "postgres2", "postgres", "admin", DBVendor.POSTGRESQL);

            System.out.println("Connected DBs:");
            hdbs.getConnectedDbNames().forEach(db -> System.out.println(db));

            hdbs.addGlobalRelationship("mysql1.customer.id", "mysql2.loyalty_card.person_id");
            hdbs.addGlobalRelationship("postgres2.place.id", "mysql1.customer.place_id");
            hdbs.addGlobalRelationship("mysql1.customer.place_id", "postgres2.place.name");//this should fail
            System.out.println(hdbs.getGlobalSchemaXML());

            hdbs.removeGlobalRelationship("mysql1.customer.id", "mysql2.loyalty_card.person_id");
            hdbs.removeGlobalRelationship("postgres2.place.id", "mysql1.customer.place_id");

            System.out.println(hdbs.getGlobalSchemaXML());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
