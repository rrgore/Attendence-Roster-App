package group13Roster;

import java.io.File;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
*@author 
*This FileOperations class is responsible for file operations like open or save.
*/
public class FileOperations extends JFileChooser {

    private JFileChooser FileSelector;
    private FileNameExtensionFilter CsvCheck;
	/*
	*This is a constructor for the FileOperations class
	*/
    public FileOperations() {
    	FileSelector = new JFileChooser();
    	CsvCheck = new FileNameExtensionFilter("CSV", "csv");
    	FileSelector.setFileFilter(CsvCheck);
    }
	/*
	*@param None
	*@returnType returns File name given by the user
	*This getSavedFilePath function opens a File selection dialog and allows user to open the custom file/
	*/
    public String getSavedFilePath() {
    	int status = FileSelector.showSaveDialog(getParent());
        if (status != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File csvFile = FileSelector.getSelectedFile();
        if (csvFile == null) {
            return null;
        }
		
		//Extracting file name
        Path dir = csvFile.toPath().getParent();
        String fileName = csvFile.getName();
        String[] buff = fileName.split("\\.");
    	if(!buff[buff.length-1].equalsIgnoreCase("csv")){
    		fileName += ".csv";
    	}
		
		//Getting file path
    	Path csvPath = dir.resolve(fileName);
    	csvFile = new File(csvPath.toString());
    	fileName = csvFile.getAbsolutePath();
    	System.out.println("Data file saved to the path: "+fileName);

        return fileName;
    }

	/*
	*@param None
	*@returnType returns path given by user
	*This getFilePath function displays a File selection dialog and allows user to save the file at custom location.
	*/
    public String getFilePath() {
    	int status = FileSelector.showOpenDialog(getParent());
        File csvFile = FileSelector.getSelectedFile();
        String name = null;
        String filePath = null;
        if (status == JFileChooser.APPROVE_OPTION) {
			//Extracting file name and custom destination location
        	name = csvFile.getName();
        	String[] buff = name.split("\\.");
        	name = buff[buff.length-1];
            if (name != null && name.equals("csv")) {
            	filePath = csvFile.getAbsolutePath();
            }
        } 
        return filePath;
    }
}
