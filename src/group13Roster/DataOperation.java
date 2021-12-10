package group13Roster;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;


public class DataOperation extends Observable {

    public static List<AttendeeData> studentDataList;
    public static List<String> titleText;
    public static int studentsAdded = 0;
    public static LinkedHashMap<String, Integer> additionalStudents;
    public static List<LocalDate> dates;

    public static final String StringDelimeter = ",";
    public static final int headerIndex = 6;

    public DataOperation() {
        titleText = new ArrayList<>();
        titleText.add("ID");
        titleText.add("First Name");
        titleText.add("Last Name");
        titleText.add("Program");
        titleText.add("Level");
        titleText.add("ASURITE");
        additionalStudents = new LinkedHashMap<>();
        dates = new ArrayList<LocalDate>();
    }


    public void uploadCsvFile(String csvFilePath) {
        try {
        	 File csvFile = new File(csvFilePath);
             FileReader reader = new FileReader(csvFile);
             BufferedReader bufferReder = new BufferedReader(reader);
             
            List<AttendeeData> stdList = new ArrayList<>();
            titleText = new ArrayList<>();
            titleText.add("ID");
            titleText.add("First Name");
            titleText.add("Last Name");
            titleText.add("Program");
            titleText.add("Level");
            titleText.add("ASURITE");

            String readLine = bufferReder.readLine();
            String[] StudentData = readLine.split(StringDelimeter);

            if (!StudentData[0].equals("ID")) {
                stdList.add(addNewStudent(StudentData));

            } else {
                for (int i = titleText.size(); i < StudentData.length; i++) {
                    titleText.add(StudentData[i]);
                }
            }
            readLine = bufferReder.readLine();
            while (readLine != null) {
                stdList.add(addNewStudent( readLine.split(StringDelimeter)));
                readLine = bufferReder.readLine();
            }

            bufferReder.close();

            studentDataList = stdList;
            setChanged();
            notifyObservers();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public AttendeeData addNewStudent(String[] attr) {
        AttendeeData student = new AttendeeData(attr[0], attr[1], attr[2], attr[3], attr[4], attr[5]);
        int i = headerIndex;
        while ( i < attr.length) {
            LocalDate date = LocalDate.parse(titleText.get(i));
            int duration = Integer.parseInt(attr[i]);
            student.setAttendanceDate(date, duration);
            i++;
        }

        return student;
    }


    public List<String> getTitleText(){
    	return this.titleText;
    }


    public String[] getTitleTexts() {
        return titleText.toArray(new String[0]);
    }

    public  List<List<String>> getStudentData() {

       
        List<List<String>> stDataArr = new ArrayList<List<String>>();
        List<String> studentAttr = new ArrayList<String>();
        for (int i = 0; i < studentDataList.size(); i++) {
        	studentAttr = new ArrayList<String>();
            studentAttr.add(studentDataList.get(i).getID());
            studentAttr.add( studentDataList.get(i).getFirstName());
            studentAttr.add(studentDataList.get(i).getLastName());
            studentAttr.add(studentDataList.get(i).getProgram());
            studentAttr.add(studentDataList.get(i).getLevel());
            studentAttr.add(studentDataList.get(i).getASURITE());         

           
            for (Map.Entry<LocalDate, Integer> e : studentDataList.get(i).getAttendance().entrySet()) {
                studentAttr.add(Integer.toString(e.getValue()));
              
            }

            stDataArr.add(studentAttr);
        }

        return stDataArr;
    }


    public void modifyStudentAtt(String fpath, LocalDate date) {

        try {
            File f = new File(fpath);
            FileReader freader = new FileReader(f);
            BufferedReader buffReader = new BufferedReader(freader);

            String asurite = "";
            int time = 0;

            String dateStr = date.toString();
            if (!titleText.contains(dateStr)) {
                titleText.add(dateStr);
            }

            String row = buffReader.readLine();
            while (row != null) {
            	String attData[] = row.split(StringDelimeter);
                asurite = attData[0];

                if (attData[1].equals("")) {
                    time = 0;
                } else {
                	
                    time = Integer.parseInt(attData[1]);
                }

                additionalStudents.put(asurite, time);

                addStudentAttHelper(date, time, asurite);
               

                row = buffReader.readLine();
            }

            buffReader.close();
            setChanged();
            notifyObservers();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addStudentAttHelper(LocalDate date, int time, String asuId ) {
    	
    	 for (AttendeeData st : studentDataList) {
             st.setAttendanceDate(date, 0);

             String str =st.getASURITE();
             if (str.equals(asuId)) {
                 st.setAttendanceDate(date, time);
                 studentsAdded += 1;
                 additionalStudents.remove(asuId);
                 

                 boolean hasDate = false;
                 for (LocalDate d : dates) {
                     if ((d).equals(date)) {
                         hasDate = true;
                     }
                 }
                 if (!hasDate) {
                     dates.add(date);
                 }
             }
         }
    }
    
    public List<AttendeeData> getRosterData(){
    	return this.studentDataList;
    }


	public List<Double> getDataSet(LocalDate local_date) {
		// TODO Auto-generated method stub
		 List<Double> xAxis = new ArrayList();

	        for (AttendeeData student : studentDataList) {
	            if (student.getDateAttendance(local_date) >= 75) {
	                xAxis.add(100.0);
	            } else {
	                double percentage = student.getDateAttendance(local_date) / 75.0 * 100;
	                xAxis.add(percentage);
	            }
	        }
	        return xAxis;
	}
    
}

