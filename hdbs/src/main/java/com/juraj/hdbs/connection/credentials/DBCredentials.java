package com.juraj.hdbs.connection.credentials;

/** Interface for database log in credentials
 * @author Juraj
*/
public interface DBCredentials {

    /** Checks if credentials are valid
     * @return true on validity; false on invalidity
     */
    boolean isValid();

    /** Gets the server url
     * @return String of server url
     */
    String getServerURL();

    /** Gets the database name
     * @return String of database name
     */
    String getDbName();

    /** Gets username
     * @return String of username
     */
    String getUsername();

    /** Gets password
     * @return String of password
     */
    String getPassword();
}
