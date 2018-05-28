package com.juraj.hdbs.querying.globalization;

import com.juraj.hdbs.querying.dataSets.DataSet;
import com.juraj.hdbs.querying.dataSets.ResultDataSet;
import com.juraj.hdbs.querying.queryComponents.*;
import com.juraj.hdbs.utils.customExceptions.ResultGlobalizationException;
import org.apache.commons.jexl3.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Contains methods used for result globalization
 */
public class ResultGlobalizer {

    /** Joins ResultDataSets into one ResultDataSet by given joins
     * @param resultDataSets List of ResultDataSet acquired by local queries
     * @param globalJoins List of global joins
     * @return A join ResultDataSet
     * @throws ResultGlobalizationException Thrown in case of badly defined joins
     */
    public static ResultDataSet joinByGlobal(List<ResultDataSet> resultDataSets, List<Join> globalJoins) throws ResultGlobalizationException {
        List<ResultDataSet> joiningResultDataSets = new ArrayList<>(resultDataSets);
        for (Join join : globalJoins){
            ResultDataSet fkDataSet = joiningResultDataSets.stream()
                    .filter(r -> r.hasColumnName(join.getForeignKeyId()))
                    .findFirst().orElse(null);
            ResultDataSet pkDataSet = joiningResultDataSets.stream()
                    .filter(r -> r.hasColumnName(join.getPrimaryKeyId()))
                    .findFirst().orElse(null);

            if (fkDataSet == null || pkDataSet == null){
                throw new ResultGlobalizationException("No data sets found to join on: " + join.getForeignKeyId() + ":" + join.getPrimaryKeyId());
            }

            ResultDataSet joinedDataSet = fkDataSet.joinWithResultDataSet(join.getForeignKeyId(), join.getPrimaryKeyId(), pkDataSet);

            joiningResultDataSets.remove(fkDataSet);
            joiningResultDataSets.remove(pkDataSet);
            joiningResultDataSets.add(joinedDataSet);
        }

        if (joiningResultDataSets.size() != 1) {
            throw new ResultGlobalizationException("Global join result is not a single data set - joins might not be connected.");
        }

        return joiningResultDataSets.get(0);
    }

    /** Creates a final global projection of results
     * @param dataSet A data set
     * @param projectionColumnNames Names of columns that are projected
     * @return A DataSet with projected data
     */
    public static DataSet getGlobalProjection(DataSet dataSet, List<String> projectionColumnNames) {

        return dataSet.getProjectedDataSet(projectionColumnNames);


    }

    /** Filters the data set by a global where clause - uses Apache JEXL3
     * @param dataSet Data set to be filtered
     * @param whereClause A global where clause
     * @return Filtered DataSet
     */
    public static DataSet globalFilter(DataSet dataSet, WhereClause whereClause){
        String booleanExpressionText = createBoolExpressionText(whereClause);

        JexlEngine jexlEngine = new JexlBuilder().create();

        JexlExpression e = jexlEngine.createExpression(booleanExpressionText);
        List<Map<String, Object>> filteredData = new ArrayList<>();
        for (Map<String, Object> row : dataSet.getData()){
            JexlContext jc = new MapContext();
            jc.set("row", row);

            Object o = e.evaluate(jc);
            if ((boolean)o){
                filteredData.add(row);
            }
        }

        if (filteredData.size() == 0){
            return new DataSet(dataSet.getColumnNames());
        } else {
            return new DataSet(filteredData);
        }


    }

    /** Helper method that turns a where clause into a text of a java boolean expression
     * @param whereClause Where clause
     * @return String of java boolean expression
     */
    private static String createBoolExpressionText(WhereClause whereClause){
        String booleanExpressionText = "";

        List<Condition> conditions = whereClause.getConditions();
        List<LogicalOperator> logicalOperators = whereClause.getLogicalOperators();

        for (int i = 0; i < conditions.size(); i++) {
            if (i > 0) {
                booleanExpressionText += logicalOperators.get(i - 1).getValue();
            }
            Condition condition = conditions.get(i);

            if (condition.getComparisonOperator() == ComparisonOperator.EQUAL) {
                booleanExpressionText += String.format("row[\"%s\"].equals(%s)", condition.getVariable(), condition.getValue().replace("'", "\""));
            } else if (condition.getComparisonOperator() == ComparisonOperator.NOT_EQUAL) {
                booleanExpressionText += String.format("!row[\"%s\"].equals(%s)", condition.getVariable(), condition.getValue().replace("'", "\""));
            } else {
                booleanExpressionText += String.format("row[\"%s\"]%s%s", condition.getVariable(), condition.getComparisonOperator().getValue(), condition.getValue().replace("'", "\""));
            }
        }
        return booleanExpressionText;
    }
}
