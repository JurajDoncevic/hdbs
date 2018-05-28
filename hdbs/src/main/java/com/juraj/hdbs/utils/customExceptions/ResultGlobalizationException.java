package com.juraj.hdbs.utils.customExceptions;

/** Thrown on error during globalization of query local results
 * @author Juraj
 */
public class ResultGlobalizationException extends Exception{
    public ResultGlobalizationException(String message) {
        super(message);
    }
}
