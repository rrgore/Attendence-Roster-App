package group13Roster;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 * @author Bhavitha Koduru
 * This SetPanel class is used to create panel in the main menu that consists of the Table data.
 */
public class SetPanel extends JPanel implements Observer {
    
	 JTable table;
	 JLabel label;
	/*
	 * This SetPanel is the constructor for creating a scrollpane for the Student Data Table
	 * @param None
	 */
    public SetPanel() {
    	
    	setLayout(new BorderLayout());
    	
    	Dimension defaultDimension = new Dimension();
		defaultDimension.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 1.5, Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1.5);
		setPreferredSize(defaultDimension);
		
		//Creating a panel screen
		Dimension panelScreen = new Dimension();
		panelScreen.setSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    	setSize(panelScreen);
		
    	//Adding scroll panel to the table
    	this.label = new JLabel();
    	this.table = new JTable();
    	
    	JScrollPane scrollPane = new JScrollPane(table);
    	scrollPane.setSize(panelScreen);
    	add(scrollPane);
    	add(label);
    }
   
    /*
     * This update class is used to indicate  an observable object in the model. 
     * @param o Indicates the observer for this object
     * @param arg Indicates if the object has been changed
     */
    public void update(Observable o, Object arg) {
    	 List<List<String>> tableDataList = ((DataOperation)o).getStudentData();
    	String[][] studentsData = new String[tableDataList.size()][];
    	String[] eachStudentData = null;
    	String[] headers = ((DataOperation)o).getTitleTexts();
    	
    	int index = 0;
    	int totalStudent = 0;
    	for(List<String> stdLst : tableDataList) {
    		eachStudentData = new String[stdLst.size()];
    		index = 0;
    		for(String str : stdLst) {
    			eachStudentData[index++] = str;
    		 
    		}
    		
    		studentsData[totalStudent++] = eachStudentData; 
    		
    	}
    	/* log the data for reference */
    	System.out.println("\nData Uploaded Successfully...");
    	
    	this.label.setText("Data Added");
    	this.table.setModel(new DefaultTableModel(studentsData, headers));
    	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
}