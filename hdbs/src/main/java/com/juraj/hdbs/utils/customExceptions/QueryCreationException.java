package com.juraj.hdbs.utils.customExceptions;

/** Thrown on a error during query creation (lexic, syntactic, semantic)
 * @author Juraj
 */
public class QueryCreationException extends Exception {

    public QueryCreationException(String message){
        super(message);
    }
}
