package com.juraj.hdbs.utils.customExceptions;

/** Thrown when there is an error during execution of a query on a local database (directly on a jdbc connection)
 * @author Juraj
 */
public class LocalQueryExecutionException extends Exception {
    public LocalQueryExecutionException(String message) {
        super(message);
    }
}
