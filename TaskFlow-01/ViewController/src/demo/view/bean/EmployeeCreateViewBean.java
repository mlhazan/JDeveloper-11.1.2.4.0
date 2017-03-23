package demo.view.bean;

import oracle.adf.view.rich.component.rich.input.RichInputText;

public class EmployeeCreateViewBean {
    private RichInputText employeeName;

    public EmployeeCreateViewBean() {
    }

    public void setEmployeeName(RichInputText employeeName) {
        this.employeeName = employeeName;
    }

    public RichInputText getEmployeeName() {
        return employeeName;
    }
}
