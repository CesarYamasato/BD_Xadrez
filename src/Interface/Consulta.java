package Interface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import org.jfree.data.category.DefaultCategoryDataset;

import SQLArgs.QueryArgs;
import SQLArgs.StatementArgs;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.out;

public class Consulta{
    private Connection conn = null;

    public boolean connect(String address, String nome_bd, String user, String password) {
        //localhost:5432
        String url = "jdbc:postgresql://"+ address + "/" + nome_bd;
        try {
            conn = DriverManager.getConnection(url, user, password);
            return true;
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Consulta.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public void getMoveCount(){
        String command = "select numjogo, count(idmov) from movimento group by numjogo order by numjogo;";
        ResultSet resultSet = makeQuery(command);

        try{DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while(resultSet.next()){
                int count = resultSet.getInt("count");
                int idjogo = resultSet.getInt("numjogo");
                dataset.addValue(count, "count",Integer.toString(idjogo));
            }
            new lineChart(dataset,"jogos x num. movimentos", "id jogo", "num. movimentos");
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("Result set is null, unable to create chart");
        }
    }

    public void getPlayerCount(){
        String command = "select nomepais, count(codpais) from participante, pais where codpais = numpais and numassoc not in (select numassoc from arbitro) group by nomepais;";
    
        ResultSet resultSet = makeQuery(command);
        try{DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while(resultSet.next()){
                int count = resultSet.getInt("count");
                String nomepais = resultSet.getString("nomepais");
                dataset.addValue(count, "count",nomepais);
            }
            new lineChart(dataset,"pais x num. jogadores", "pais", "num. jogadores");
        }
        catch(SQLException e){
            out.println("Result set is null, unable to create chart");
        }
    }

    public void getPlayerTable(){
        String command = "select numassoc, nomeassoc, nivel from jogador natural join participante";
        QueryArgs args = new QueryArgs();
        args.addInt("numassoc");
        args.addString("nomeassoc");
        args.addInt("nivel");
        String[] columnNames = {"Participant ID", "Player name", "Level"};
        Object[][] resulObjects = Query(command, args);
        new Table("Players",resulObjects, columnNames);
    }

    public void getGamesTable(){
        String command = "select distinct on (temp.numjogo) temp.numjogo ,part1.nomeassoc as jogador1, temp.cor1 as cor1, part2.nomeassoc as jogador2, temp.cor2 as cor2 from" +
        "                 (select distinct T.numjogo ,T.numassoc as jogador1, T.cor as cor1, B.numassoc as jogador2, B.cor as cor2"+
        "                 from joga as T full outer join joga as B on T.numjogo = B.numjogo where T.numassoc != B.numassoc) as temp" +
        "                 join participante as part1 on jogador1 = part1.numassoc join participante as part2 on jogador2 = part2.numassoc";
        QueryArgs args = new QueryArgs();
        args.addInt("numjogo");
        args.addString("jogador1");
        args.addString("cor1");
        args.addString("jogador2");
        args.addString("cor2");
    
        String[] columnNames = {"Match ID", "Palyer 1", "color", "Player 2", "color"};
        Object[][] resulObjects = Query(command, args);
        new Table( "Matches" ,resulObjects, columnNames);
        
    }

    public void getParticipantsTable(){
        String command = "select distinct on (numassoc) numassoc, nomeassoc, nomepais from participante join pais on codpais = numpais";
        QueryArgs args = new QueryArgs();
        args.addInt("numassoc");
        args.addString("nomeassoc");
        args.addString("nomepais");

        String[] columnNames = {"Participant ID", "Name", "Pais"};
        Object[][] resulObjects = Query(command, args);
        if(resulObjects != null)new Table( "Participants" ,resulObjects, columnNames);
    }

    public void getCountryTable(){
        String command = "select * from PAIS";
        QueryArgs args = new QueryArgs();
        args.addInt("numpais");
        args.addString("nomepais");
        args.addInt("numclubes");
        
        String[] columnNames = {"Country ID", "Country name", "Num. clubs"};
        Object[][] resulObjects = Query(command, args);
        new Table("Countries",resulObjects, columnNames);
    }

    public void inserParticipant(String name,String address ,long tel, int idpais){
        String command = "INSERT INTO participante (nomeassoc, endereco, telcontato, codpais) VALUES (?,?,?,?)";
        StatementArgs args = new StatementArgs();
        args.add(name);
        args.add(address);
        args.add(tel);
        args.add(idpais);
        makeUpdate(command, args);
    }

    public void inserPlayer(int id, int lvl){
        String command = "INSERT INTO jogador VALUES (?,?)";
        StatementArgs args = new StatementArgs();
        args.add(id);
        args.add(lvl);
        makeUpdate(command, args);
    }

    private int makeUpdate(String command, StatementArgs args){
        try{
            PreparedStatement smt = conn.prepareStatement(command);
            args.addToStatement(smt);
            return smt.executeUpdate();
        }
        catch(SQLException e){
            out.println("Unable to update database");
            out.println("SQLState: " + e.getSQLState());
            out.println("Error description: " + e.getMessage());
            return 0;}
    }

    private Object [][] Query(String command, QueryArgs args){
        try{Statement statement = conn.createStatement();
        return args.makeQuery(statement,command);}
        catch(SQLException e){
            out.println("Unable to retrieve data from database");
            out.println("SQLState: " + e.getSQLState());
            out.println("Error description: " + e.getMessage());
            return null;}

    }

    private ResultSet makeQuery(String command){
        try{Statement statement = conn.createStatement();;
        return statement.executeQuery(command);
        }
        catch(SQLException e){
            out.println("Unable to retrieve data from database");
            out.println("SQLState: " + e.getSQLState());
            out.println("Error description: " + e.getMessage());
            return null;}
    }
}

