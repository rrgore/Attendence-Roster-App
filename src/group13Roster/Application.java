package group13Roster;
import org.jdesktop.swingx.JXDatePicker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Application extends JFrame{
	 private static final String APPLICATION_TITLE = "Attendence Roster";
	    private static final String FILE_MENU = "File";
	    private static final String ABOUT_MENU = "About";
	    private static final String LOAD_ROSTER_MENU_ITEM = "Load a Roster";
	    private static final String ADD_ATTENDANCE_MENU_ITEM = "Add Attendance";
	    private static final String SAVE_MENU_ITEM = "Save";
	    private static final String PLOT_DATA_MENU_ITEM = "Plot Data";
	    private static final String ERROR_MESSAGE = "Load the roaster first to perform this action";
	    private static final String FAILED = "Failed";
	    

	
	    protected static DataOperation dataOp;
	    
	    Dimension screenSize;
	    JMenu JFile;
	    JMenuItem About; 
	    JMenuBar JMbar;
	    
	    JMenuItem Load;
	    JMenuItem addAtt ;
	    JMenuItem save ;
	    JMenuItem plot;

	     SetPanel panel;
	    
	    public Application() {
	    	 setLayout(new BorderLayout());

	         screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	         setPreferredSize(new Dimension((int) (screenSize.getWidth()/1.5),(int) (screenSize.getHeight()/1.5) ));

	         setTitle(APPLICATION_TITLE);

	         JMbar = new JMenuBar();

	         JFile = new JMenu(FILE_MENU);
	         About = new JMenuItem(ABOUT_MENU);

	         Load = new JMenuItem(LOAD_ROSTER_MENU_ITEM);
	         addAtt = new JMenuItem(ADD_ATTENDANCE_MENU_ITEM);
	         save = new JMenuItem(SAVE_MENU_ITEM);
	         plot = new JMenuItem(PLOT_DATA_MENU_ITEM);

	         JFile.add(Load);
	         JFile.add(addAtt);
	         JFile.add(save);
	         JFile.add(plot);

	         JMbar.add(JFile);
	         JMbar.add(About);
	        
	         add(JMbar);
	         setJMenuBar(JMbar);

	         panel = new SetPanel();
	       
		       
	         add(panel, BorderLayout.CENTER);
	         
		       
	         
	       
	         pack();
	         setVisible(true);
	         
	         
	        
	         
	         dataOp = new DataOperation();
	         dataOp.addObserver(panel);

	      
	         About.addActionListener(evt -> {

	             String aboutTeam = "<html> <style>html {text-align: center} html {word-wrap: break-word} html {color:blue} </style>" +
	                     			"<h1>This Attendance Roster Project has been designed by a team of 5 members<br></h1>"+ 
	                     
	                     			"<h2>NAMED: Rahul Gore, Bhavitha Koduru, Akash Mund, Danish Pasricha, Ayush Soneria" + 
	                     			"</html>";
	        	setDisplayDialog("About", aboutTeam,1200,600);
	        });
	         

	         Load.addActionListener(evt -> {
	             String inFile =  new FileOperations().getFilePath();
	             if(inFile != null) {
	                 dataOp.uploadCsvFile(inFile);
	                 setDisplayDialog("","Roster Data uploaded Successfully",400,100);
	             }
	             
	         });

	         addAtt.addActionListener(evt -> {
	             if(DataOperation.studentDataList != null) {
	                 String inFile =  new FileOperations().getFilePath();
	                 if(inFile != null) {	 
	                 	 showDatePicker(inFile);
	                 }
	             }
	             else {
	            	 setDisplayDialog(this.FAILED,this.ERROR_MESSAGE,600,300);
	             }
	         });

	         save.addActionListener(evt -> {
	             if( DataOperation.studentDataList != null) {
	                 String inFile =  new  FileOperations().getSavedFilePath();
	                 if(inFile != null) {
	                	 try {
	                    	                     
	                     List<String> titleText = Application.dataOp.getTitleText();
	                     
	                    	 List<List<String>> tableDataList = Application.dataOp.getStudentData();
	                    	 
	                    	
	                        FileWriter fw = new FileWriter(inFile);

	                        if (!titleText.isEmpty()) {
	                        	String str = "";
	                        	 for(int j = 0; j < titleText.size(); j++) {
		                            	str = ","+titleText.get(j);
		                     
		                            }
	                        	 fw.append(str);
	                        }
	                        	
	                        	
	                        int i = 0;
	                        
	                      

	                        for (List<String> student : tableDataList) {
	                            fw.append("\n");
	                            String str = "";
	                            for(int j = 0; j < student.size(); j++) {
	                            	str = ","+student.get(j);
	                     
	                            }
	                            fw.append(str);
	                        }

	                        fw.flush();
	                        fw.close();
	                        setDisplayDialog("Saved","The file is saved to the path "+inFile,600,300);
	                    } catch (Exception e) {
	                        System.out.println(e);
	                    }
	                	 
	                	 
	                 }
	             }
	             else {
	            	 setDisplayDialog(this.FAILED,this.ERROR_MESSAGE,600,300);
	             }
	         });

	         plot.addActionListener(evt -> {
	             if( DataOperation.studentDataList != null) {
	                  PlotChart.plotData();
	             }
	             else {
	            	 setDisplayDialog(this.FAILED,this.ERROR_MESSAGE,600,300);
	             }
	         });

	        
	     }
	    
	    
	    public void setDisplayDialog(String label, String message, int x, int y) {
	    	JFrame genaralFrame = new JFrame();
	        JDialog generalDialog = new JDialog(genaralFrame, label);
	        JPanel generalPanel = new JPanel();
	        
	        JLabel generalMessage = new JLabel();
	        generalMessage.setText(message);
	        
	        generalPanel.add(generalMessage);
	        generalDialog.add(generalPanel);
	        
	        generalDialog.setSize(x, y);
	        generalDialog.setVisible(true);
	    }
	     
	    
public void showDatePicker(String path) {
    JFrame calFrame = new JFrame("Date Picker");
    JPanel calPanel = new JPanel();
    JButton calBtn = new JButton("Submit");
    JLabel calLabel = new JLabel("Choose Attendance Date");

    calFrame.setBounds(500, 500, 300, 250);

    
   
    JXDatePicker datePicker = new JXDatePicker();
    datePicker.setDate(Calendar.getInstance().getTime());
    datePicker.setFormats(new SimpleDateFormat("MM/dd/yyyy")); 

   
    calPanel.add(calLabel);
    calFrame.getContentPane().add(calPanel);
    calPanel.add(calBtn);
    calPanel.add(datePicker);
    calFrame.setVisible(true);

   
    calBtn.addActionListener(evt -> {
            Date date = datePicker.getDate(); 
                    
            LocalDate local_date = LocalDate.of(date.getYear() + 1900,  date.getMonth() + 1,  date.getDate());
        	dataOp.modifyStudentAtt(path, local_date);
        	
        	String message = "<html> <style>html {text-align: center} html {word-wrap: break-word} html {color:blue} </style>" +
 			"<h1>Attendance Summary added for "+DataOperation.studentsAdded+" students in the Roaster.<br></h1>"; 
 
        	String label = "Attendance added";
        	//String message =   "Attendance Summary displayed for " +  DataOperation.studentsAdded + " students in the Roaster.";
        	
        	if( DataOperation.additionalStudents.size() > 0) {
        		 message += "<h2>"+DataOperation.additionalStudents.size() +" additional student(s) found.<br>";
        	 			
                // message +=  "<html> <p>" +  DataOperation.additionalStudents.size()  + " additional students were found.<br></p>";
                 
           }
        	message = message + "</html>";
        	
        	String finalTextOutput = "";
            if ( DataOperation.additionalStudents.size() > 0) {
                for (Map.Entry<String, Integer> i :  DataOperation.additionalStudents.entrySet()) {
                	finalTextOutput += "<html>" + i.getKey() + ", joining for " + i.getValue() + " minute(s)" + "<br>";
                  
                }
            }
            message = message + finalTextOutput;
            setDisplayDialog(label, message,1200,600);
            
        	

            calFrame.dispose();
    });
}

}
