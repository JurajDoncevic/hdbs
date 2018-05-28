package com.juraj.hdbs.utils.customExceptions;

/** Exception thrown when there is an error during a setup of a connection to a database
 * @author Juraj
 */
public class ConnectionSetupException extends Exception {
    public ConnectionSetupException(String message){
        super(message);
    }
}
