package SQLArgs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

interface queryInterface{
    public Object getVar(ResultSet set);
}

abstract class queryResult implements queryInterface{
    String name;

    public void printErrorMessage(SQLException e){
        System.out.println("Unable to retrieve value from column" + name + " from database");
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("Error description: " + e.getMessage());
    }
}

class queryIntegerResult extends queryResult{

    queryIntegerResult(String name) {
        this.name = name;
    }

    @Override
    public Object getVar(ResultSet set) {
        try{return set.getInt(name);}
        catch(SQLException e){
            printErrorMessage(e);
            return -1;
        }
    }
}

class queryBooleanResult extends queryResult{

    queryBooleanResult(String name) {
        this.name = name;
    }

    @Override
    public Object getVar(ResultSet set) {
        try{return set.getBoolean(name);}
        catch(SQLException e){
            printErrorMessage(e);
            return -1;
        }
    }
}

class queryStringResult extends queryResult{

    queryStringResult(String name) {
        this.name = name;
    }

    @Override
    public Object getVar(ResultSet set) {
        try{return set.getString(name);}
        catch(SQLException e){
            printErrorMessage(e);
            return -1;
        }
    }
}

class queryDateResult extends queryResult{

    queryDateResult(String name) {
        this.name = name;
    }

    @Override
    public Object getVar(ResultSet set) {
        try{return set.getDate(name);}
        catch(SQLException e){
            printErrorMessage(e);
            return -1;
        }
    }
}

class queryLongResult extends queryResult{

    queryLongResult(String name) {
        this.name = name;
    }

    @Override
    public Object getVar(ResultSet set) {
        try{return set.getLong(name);}
        catch(SQLException e){
            printErrorMessage(e);
            return -1;
        }
    }
}

public class QueryArgs {
    private LinkedList<queryInterface> queryArgs = new LinkedList<queryInterface>();
    private LinkedList<LinkedList<Object>> resultTable = new LinkedList<LinkedList<Object>>();
    private int rows = 0;
    private int columns = 0;

    public void addInt(String name){
        queryArgs.add(new queryIntegerResult(name));
        columns++;
    }

    public void addBoolean(String name){
        queryArgs.add(new queryBooleanResult(name));
        columns++;
    }

    public void addString(String name){
        queryArgs.add(new queryStringResult(name));
        columns++;
    }

    public void addDate(String name){
        queryArgs.add(new queryDateResult(name));
        columns++;
    }

    public void addLong(String name){
        queryArgs.add(new queryLongResult(name));
        columns++;
    }

    public Object[][] makeQuery(Statement statement, String command) throws SQLException{
        ResultSet resultSet = statement.executeQuery(command);
        while(resultSet.next()){
            LinkedList<Object> row = new LinkedList<Object>();
            for(queryInterface arg : queryArgs) row.add(arg.getVar(resultSet));
            resultTable.add(row);
            rows++;
        }
        int index = 0;
        Object[][] result = new Object[rows][columns];
        for(LinkedList<Object> row : resultTable){
            result[index] = row.toArray();
            index++;
        }
        return result;
    }
}
