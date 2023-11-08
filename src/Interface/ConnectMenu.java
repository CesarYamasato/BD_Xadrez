package Interface;
import java.awt.Button;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ConnectMenu extends Menu{
    Consulta dataBase;
    Button connectButton = new Button("Connect");
    ArrayList<TextField> TextFields = new ArrayList<TextField>();
    public ConnectMenu(){
        super("Chess database", 400, 340);

        TextFields.add(new TextField("IP address:port",18));
        TextFields.add(new TextField("Database name",40));
        TextFields.add(new TextField("User", 40));
        TextFields.add(new TextField("Password" ,40));

        addTextFieldList(TextFields, 50, 55 ,300, 30,10);

        connectButton.setBounds(100, 55+TextFields.size()*(30+10), 200, 100);
        connectButton.addActionListener(this);

        add(connectButton);

        dataBase = new Consulta();
    }

    @Override
    public void actionPerformed(ActionEvent action) {
        String address = TextFields.get(0).getText();
        String nameDB = TextFields.get(1).getText();
        String user = TextFields.get(2).getText();
        String password = TextFields.get(3).getText();

        if(dataBase.connect(address, nameDB, user, password)) {
            new MainMenu(dataBase);
            dispose();
        }
    }
    
}