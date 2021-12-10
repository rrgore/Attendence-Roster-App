package group13Roster;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;

//this class hold the generic data about the student
public class AttendeeData {

	private final boolean SUCCESS = true;
    private HashMap<LocalDate, Integer> attendance;


    private String student_ID;
    private String first_Name;
    private String last_Name;
    private String program_Plan;
    private String academic_Level;
    private String ASURITE;


    public AttendeeData(
            String student_ID,
            String first_Name,
            String last_Name,
            String program_Plan,
            String academic_Level,
            String ASURITE) {
    	
        this.student_ID = student_ID;
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.program_Plan = program_Plan;
        this.academic_Level = academic_Level;
        this.ASURITE = ASURITE;
        this.attendance = new LinkedHashMap();
    }


    public boolean setAttendanceDate(LocalDate date, int time) {
        attendance.put(date, attendance.getOrDefault(date, 0) + time);
        return SUCCESS;
    }

    // getting dateAttendance
    public int getDateAttendance(LocalDate date) {
        return attendance.get(date);
    }

    // creating a map named getAttendance in which local date has got integer values
    public HashMap<LocalDate, Integer> getAttendance() {
        return new LinkedHashMap(attendance);
    }


    public String getID() { // returning student id
        return this.student_ID;
    }


    public boolean setID(String student_ID) { // this is refering to the current object(Student_id) in the method.
        this.student_ID = student_ID;
        return SUCCESS;
    }


    public String getFirstName() { // returning first name
        return this.first_Name;
    }


    public boolean setFirstName(String first_Name) {  // this is refering to the current object(first_name) in the method.
        this.first_Name = first_Name;
        return SUCCESS;
    }

    public String getLastName() {  // returning last name
        return this.last_Name;
    }


    public boolean setLastName(String last_Name) {  // this is refering to the current object(last_name) in the method.
        this.last_Name = last_Name;
        return SUCCESS;
    }


    public String getProgram() { //return program plan
        return program_Plan;
    }


    public boolean setProgram(String program_Plan) {  // this is refering to the current object(program_plan) in the method.
        this.program_Plan = program_Plan;
        return SUCCESS;
    }


    public String getLevel() { // return academic_level
        return academic_Level;
    }

    //
    public boolean setLevel(String academic_Level) {  // this is refering to the current object(academic_level) in the method.
        this.academic_Level = academic_Level;
        return SUCCESS;
    }


    public String getASURITE() { // returning asurite id
        return this.ASURITE;
    }

    public boolean setASURITE(String ASURITE) {  // this is refering to the current object(ASURITE_id) in the method.
        this.ASURITE = ASURITE;
        return SUCCESS;
    }
}
