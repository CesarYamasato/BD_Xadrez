package SQLArgs;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

import static java.lang.System.out;

interface ArgsInterface{
    public void insertArg(PreparedStatement stmt, int index) throws SQLException;
}

class IntegerArgument implements ArgsInterface{
    int value;
    public IntegerArgument(int arg){value = arg;}
    public void insertArg(PreparedStatement stmt, int index) throws SQLException{
        stmt.setInt(index, value);
    }
    
}
class StringArgument implements ArgsInterface{
    String value;
    public StringArgument(String arg){value = arg;}
    public void insertArg(PreparedStatement stmt, int index) throws SQLException{
        stmt.setString(index, value);
    }
    
}

class DateArgument implements ArgsInterface{
    Date value;
    public DateArgument(Date arg){value = arg;}
    public void insertArg(PreparedStatement stmt, int index) throws SQLException{
        stmt.setDate(index, value);
    }
    
}

class BooleanArgument implements ArgsInterface{
    boolean value;
    public BooleanArgument(boolean arg){value = arg;}
    public void insertArg(PreparedStatement stmt, int index) throws SQLException{
        stmt.setBoolean(index, value);
    }
    
}

class LongArgument implements ArgsInterface{
    long value;
    public LongArgument(long arg){value = arg;}
    public void insertArg(PreparedStatement stmt, int index) throws SQLException{
        stmt.setLong(index, value);
    }
    
}
public class StatementArgs{
    private LinkedList<ArgsInterface> args = new LinkedList<ArgsInterface>();

    public void add(int arg){
        ArgsInterface argument = new IntegerArgument(arg);
        args.add(argument);
    }
    public void add(String arg){
        ArgsInterface argument = new StringArgument(arg);
        args.add(argument);
    }
    public void add(Date arg){
        ArgsInterface argument = new DateArgument(arg);
        args.add(argument);
    }
    public void add(boolean arg){
        ArgsInterface argument = new BooleanArgument(arg);
        args.add(argument);
    }

    public void add(long arg){
        ArgsInterface argument = new LongArgument(arg);
        args.add(argument);
    }

    public boolean addToStatement(PreparedStatement statement) throws SQLException{
        int index = 0;
        for(ArgsInterface arg : args){
            index++;
            try{arg.insertArg(statement, index);}
            catch(SQLException e){
                out.println("Unable to add all statement arguments");
                out.println("SQLState: " + e.getSQLState());
                out.println("Error description: " + e.getMessage());
                return false;
            }
        }
        return true;
    }
}