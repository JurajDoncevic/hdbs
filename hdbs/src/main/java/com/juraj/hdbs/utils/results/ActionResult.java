package com.juraj.hdbs.utils.results;

/** Action result class that describes the outcome of all actions done by the heterogeneous database system
 * @author Juraj
 */
public class ActionResult {

    private ActionResultType actionResultType;
    private String message;
    private String data;

    /** Constructor
     * @param actionResultType Result type
     * @param message Result message
     */
    public ActionResult(ActionResultType actionResultType, String message) {
        this.actionResultType = actionResultType;
        this.message = message;
        this.data = "";
    }

    /** Constructor
     * @param actionResultType Result type
     * @param message Result message
     * @param data Data that is the result of an action
     */
    public ActionResult(ActionResultType actionResultType, String message, String data) {
        this.actionResultType = actionResultType;
        this.message = message;
        this.data = data;
    }

    /** Gets the result type
     * @return ActionResultType
     */
    public ActionResultType getActionResultType() {
        return actionResultType;
    }

    /** Gets the result message
     * @return Message
     */
    public String getMessage() {
        return message;
    }

    /** Formatted result outcome
     * @return String containing result type and message
     */
    public String toString(){
        return actionResultType + ":" + message;
    }

    /** Gets the data of the result
     * @return String of data
     */
    public String getData() {
        return data;
    }
}
