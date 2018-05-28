package com.juraj.hdbs.utils.customExceptions;

/** Thrown in case of an invalid type input for an aggregation function
 * @author Juraj
 */
public class InvalidAggregateDataTypeException extends Exception {
    public InvalidAggregateDataTypeException(String message) {
        super(message);
    }

}
