package Interface;


import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

class MainMenu extends Menu{
    Consulta dataBase;
    ArrayList<Button> buttons1 = new ArrayList<Button>();
    ArrayList<Button> buttons2 = new ArrayList<Button>();
    public MainMenu(Consulta dataBase){
        super("Chess database", 550, 430);
        this.dataBase = dataBase;

        buttons1.add(new Button("Add participant"));
        buttons1.add(new Button("Add player"));
        buttons1.add(new Button("Get players per country"));
        buttons1.add(new Button("Get moves per game"));

        buttons2.add(new Button("Get country list"));
        buttons2.add(new Button("Get player List"));
        buttons2.add(new Button("Get matches list"));
        buttons2.add(new Button("Get participants list"));
        
        addButtonList(buttons1, 50, 55, 200, 80, 10);
        addButtonList(buttons2, 300, 55, 200, 80, 10);

        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we) {System.exit(0);}
            }
        );
    }

    @Override
    public void actionPerformed(ActionEvent action) {
        
        if(action.getSource() == buttons1.get(0)){
            new AddParticipant(dataBase);
        }
        else if(action.getSource() == buttons1.get(1)){
            new AddPlayer(dataBase);
        }
        else if(action.getSource() == buttons1.get(2)){
            dataBase.getPlayerCount();
        }
        else if(action.getSource() == buttons1.get(3)){
            dataBase.getMoveCount();
        }
        else if(action.getSource() == buttons2.get(0)){
            dataBase.getCountryTable();
        }
        else if(action.getSource() == buttons2.get(1)){
            dataBase.getPlayerTable();
        }
        else if(action.getSource() == buttons2.get(2)){
            dataBase.getGamesTable();
        }
        else if(action.getSource() == buttons2.get(3)){
            dataBase.getParticipantsTable();
        }
    }
}

