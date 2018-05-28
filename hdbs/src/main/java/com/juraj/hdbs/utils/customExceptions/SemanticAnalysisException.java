package com.juraj.hdbs.utils.customExceptions;

/** Inner system exception thrown on a semantic error for a query
 * @author Juraj
 */
public class SemanticAnalysisException extends Exception {

    public SemanticAnalysisException(String message){
        super(message);
    }
}
