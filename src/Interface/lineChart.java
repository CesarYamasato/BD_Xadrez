package Interface;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class lineChart extends ApplicationFrame{
    JFrame frame;
    public lineChart(DefaultCategoryDataset dataset, String title, String titleX, String titleY){
        super(title);
        frame = new JFrame(title);
        
        JFreeChart lineChart = ChartFactory.createLineChart( title,
            titleX,titleY,
            dataset,
            PlotOrientation.VERTICAL,
            true,true,false);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        frame.setTitle(title);
        frame.setLayout(new BorderLayout(0,1));
        frame.add(chartPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
