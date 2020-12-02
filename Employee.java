package sample;

public class Employee {

    private int employeeId;
    private String employeeName;
    private String employeeCar;
    private String carInsurance;
    private String carPit;

    public Employee(int employeeId, String employeeName, String employeeCar, String carInsurance, String carPit) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeCar = employeeCar;
        this.carInsurance = carInsurance;
        this.carPit = carPit;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCar() {
        return employeeCar;
    }

    public void setEmployeeCar(String employeeCar) {
        this.employeeCar = employeeCar;
    }

    public String getCarInsurance() {
        return carInsurance;
    }

    public void setCarInsurance(String carInsurance) {
        this.carInsurance = carInsurance;
    }

    public String getCarPit() {
        return carPit;
    }

    public void setCarPit(String carPit) {
        this.carPit = carPit;
    }
}
