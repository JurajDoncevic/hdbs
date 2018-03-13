package com.juraj.hdbs.schemaManagement;

import com.juraj.hdbs.schemaManagement.metamodeling.GlobalSchema;

/**
 * Created by Juraj on 13.3.2018..
 */
public class CurrentGlobalSchema {

    private static GlobalSchema globalSchema;

    public static GlobalSchema getGlobalSchema() {
        return globalSchema;
    }

    public static void setGlobalSchema(GlobalSchema globalSchema) {
        CurrentGlobalSchema.globalSchema = globalSchema;
    }

}
