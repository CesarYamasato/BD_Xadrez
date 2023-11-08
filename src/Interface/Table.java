package Interface;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Table extends JPanel{
    final private JTable table;
    final private JScrollPane scrollPane;
    private String[] columnNames;
    private Object[][] data;
    public Table(String title, Object[][] data, String[] columnNames){
        table = new JTable(data, columnNames);
        this.columnNames = columnNames;
        this.data = data;

        table.setFont(new Font ("Arial", Font.PLAIN, 12));
        adjustTableSize();

        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        add(scrollPane);

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setOpaque(true);
        frame.setContentPane(this);

        frame.pack();
        frame.setVisible(true);
    }

    private void adjustTableSize(){
        FontMetrics metrics = this.getFontMetrics(getFont());
        int width = 0;
        for(int i = 0; i < columnNames.length; i++){
            int columnWidth = metrics.stringWidth(columnNames[i]) + 60;
            table.getColumnModel().getColumn(i).setWidth(columnWidth);
            width += columnWidth;
        }
        table.setPreferredScrollableViewportSize(new Dimension(width, data.length*15+20));
    }
}
