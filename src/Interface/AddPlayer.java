package Interface;

import java.awt.Button;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AddPlayer extends Menu{
    Consulta dataBase;
    Button addPlayer = new Button("Add");
    ArrayList<TextField> textFields = new ArrayList<TextField>();
    protected AddPlayer(Consulta dataBase) {
        super("Add player", 400, 330);
        this.dataBase = dataBase;

        textFields.add(new TextField("Participant Id", 10));
        textFields.add(new TextField("Player level", 10));

        addTextFieldList(textFields, 50, 55, 300, 30, 10);
        addPlayer.setBounds(100, 135, 200, 80);
        addPlayer.addActionListener(this);
        add(addPlayer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int id = Integer.parseInt(textFields.get(0).getText());
        int lvl = Integer.parseInt(textFields.get(1).getText());
        dataBase.inserPlayer(id, lvl);
    }
    
}
