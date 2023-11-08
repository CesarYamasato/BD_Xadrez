package Interface;

import java.awt.*;
import java.awt.Button;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;

public abstract class Menu extends Frame implements ActionListener{

    protected Menu(String title, int width, int height){
        setFont(new Font("Arial", Font.PLAIN, 12));
        String Title = centerText(this, title);
        setTitle(Title);
        setLayout(null);
        setSize(width, height);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we) {dispose();}
            }
        );

        setVisible(true);
    }

    /*Creates a list of buttons at the specified posx and pos (top left corner) wich 
    contains the buttons passaed in by argument in the buttons collection argument, spaced 
    by the buttonSpacing argument.Each button is resized to the specified width and height*/
    protected void addButtonList(Collection<Button> buttons, int posx, int posy, int buttonWidth, int buttonHeight, int buttonSpacing){
        int index = 0;
        for(Button button : buttons){
            button.setBounds(
                posx, posy+index*(buttonHeight + buttonSpacing),
                buttonWidth, buttonHeight);

            add(button);
            button.addActionListener(this);
            index++;
        }
    }

    /*Creates a list of text fields at the specified posx and pos (top left corner)
    wich contains the text fieldspassaed in by argument in the textFields 
    collection, spaced by the textFieldSpacing argument.Each text field is
    resized to the specified width and height and has its text centered*/
    protected void addTextFieldList(Collection<TextField> textFields, int posx, int posy, int textFieldWidth, int textFieldHeight, int textFieldSpacing){
        int index = 0;
        for(TextField textField : textFields){
            textField.setBounds(
                posx, posy+index*(textFieldHeight+textFieldSpacing),
                textFieldWidth,textFieldHeight);

            String text = centerText(textField, textField.getText());
            textField.setText(text);
            add(textField);
            index++;
        }
    }

    protected String centerText(Component textField, String text){
        FontMetrics metrics = this.getFontMetrics(getFont());
        int width = metrics.stringWidth(text);
        int blank = metrics.stringWidth(" ");
        int z = textField.getWidth()/2 - width/2;
        int w  = z/blank;
        String result ="";
        result = String.format("%" + w + "s", result);
        result = result + text;
        return result;
    }
}
