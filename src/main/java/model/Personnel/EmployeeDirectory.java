package model.Personnel;

import java.util.ArrayList;

public class EmployeeDirectory {
    ArrayList<EmployeeProfile> employeeList;

    public EmployeeDirectory() {
        employeeList = new ArrayList<EmployeeProfile>();
    }

    public EmployeeProfile newEmployeeProfile(Person p) {
        EmployeeProfile ep = new EmployeeProfile(p);
        employeeList.add(ep);
        return ep;
    }
}
