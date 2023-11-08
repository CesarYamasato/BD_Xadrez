package Interface;
import java.awt.Button;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

class AddParticipant extends Menu{
    Consulta dataBase;
    ArrayList<TextField> TextFields = new ArrayList<TextField>();
    AddParticipant(Consulta dataBase) {
        super("Add participant", 400, 430);
        this.dataBase = dataBase;
        TextFields.add(new TextField("Name", 40));
        TextFields.add(new TextField("Addres", 40));
        TextFields.add(new TextField("Tel", 40));
        TextFields.add(new TextField("Country ID", 40));

        addTextFieldList(TextFields, 50, 55, 300, 30, 10);

        Button addParticipant = new Button("Add");
        addParticipant.addActionListener(this);
        addParticipant.setBounds(100, 55+TextFields.size()*(30+10), 200, 80);
        add(addParticipant);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String address = TextFields.get(0).getText();
        String name = TextFields.get(1).getText();
        long tel = Long.parseLong(TextFields.get(2).getText());
        int countryid = Integer.parseInt(TextFields.get(3).getText());

        dataBase.inserParticipant(address, name, tel, countryid);
    }

}