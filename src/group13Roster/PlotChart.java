
package group13Roster;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.xy.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberTickUnit;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;


public class PlotChart extends JFrame {
	XYDataset information;
	 JFreeChart scatter_plot;
	 XYPlot xy_p ;
	 NumberAxis y ;
	 NumberAxis x;
	 ChartPanel display_plot;
    public PlotChart(String heading) {
        super(heading);

        /* get xydataset to create the scatter plot */
        information = generatePlotData();
        scatter_plot =
                ChartFactory.createScatterPlot(
                        "Scatter Plot", "% of Attendance", "Number Of Students", information);
        
        xy_p = (XYPlot) scatter_plot.getPlot();
        y = (NumberAxis) xy_p.getRangeAxis();
        
        y.setVerticalTickLabels(true);
        y.setTickUnit(new NumberTickUnit(1));
        y.setRange(0, DataOperation.studentDataList.size() + 1);
        x = (NumberAxis) xy_p.getDomainAxis();
        x.setVerticalTickLabels(true);  
        x.setTickUnit(new NumberTickUnit(10));
        x.setRange(0, 120);
        xy_p.setBackgroundPaint(new Color(0, 255, 255));
        display_plot = new ChartPanel(scatter_plot);
        setContentPane(display_plot);
    }

    private XYDataset generatePlotData() {
        List<LocalDate> all_date_data = new ArrayList<LocalDate>();

        for (int i = 6; i < DataOperation.titleText.size(); i++) {
            all_date_data.add(LocalDate.parse(DataOperation.titleText.get(i)));
        }

        XYSeriesCollection xy_series_collection = new XYSeriesCollection();

        for (LocalDate local_date : all_date_data) {
            XYSeries xy_series_data = new XYSeries(local_date.toString());
          
            
            List<Double> main_data = new ArrayList<>();

            List<AttendeeData> roster =  Application.dataOp.getRosterData();
            
            for (int i=0; i< roster.size(); i++) {
                if (roster.get(i).getDateAttendance(local_date) >= 75) {
                	main_data.add(100.0);
                } else {
                    double percent = roster.get(i).getDateAttendance(local_date) / 75.0 * 100;
                    main_data.add(percent);
                }
            }
            
            int a = 0;
            while (a < main_data.size()) {
                int n = 1;
                int value_Percent = main_data.get(a).intValue();
                int b = a + 1;
                while ( b < main_data.size()) {
                    if (value_Percent == main_data.get(b).intValue()) {
                        main_data.remove(b);
                        b--;
                        n++;
                    }
                    b++;
                }
                xy_series_data.add(value_Percent, n);
                a++;
            }
            xy_series_collection.addSeries(xy_series_data);
        }
        return xy_series_collection;
    }

    public static void plotData() {
        SwingUtilities.invokeLater(
                () -> {
                    PlotChart scatter_plot_gui = new PlotChart("Plot Data");
                    scatter_plot_gui.setVisible(true);
                   
                    scatter_plot_gui.setSize(900, 475);
                });
    }
}