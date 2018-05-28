package com.juraj.hdbs.utils.customExceptions;

/** Thrown when there is a problem with retrieving connection credentials from a metadata database
 * @author Juraj
 */
public class PersistentConnectionRetrievalException extends Exception {
    public PersistentConnectionRetrievalException(String message){
        super(message);
    }
}
