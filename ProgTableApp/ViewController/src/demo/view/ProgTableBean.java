package demo.view;



import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;

public class ProgTableBean {
    //ArrayList to poplate data in af:table
    List<PersonBean> personList = new ArrayList();
    //Binding of component to get data from page
    private RichInputText nameBind;
    private RichInputText mobNumBind;
    private RichInputText salaryBind;

    //To Populate default row in table
    public ProgTableBean() {
        personList.add(new PersonBean("Ashish Awasthi", "6478902546", 50000));
        personList.add(new PersonBean("Mahmud Quaraisi", "4162456787", 40000));
        personList.add(new PersonBean("Ashish Awasthi", "6478902546", 50000));
        personList.add(new PersonBean("Mahmud Quaraisi", "4162456787", 40000));
        personList.add(new PersonBean("Ashish Awasthi", "6478902546", 50000));
        personList.add(new PersonBean("Mahmud Quaraisi", "4162456787", 40000));
        personList.add(new PersonBean("Ashish Awasthi", "6478902546", 50000));
        personList.add(new PersonBean("Mahmud Quaraisi", "4162456787", 40000));
    }

    public void setPersonList(List<PersonBean> personList) {
        this.personList = personList;
    }

    public List<PersonBean> getPersonList() {
        return personList;
    }

//    /**Method to add new record in table
//     * @param actionEvent
//     */
//    public void addNewRcord(ActionEvent actionEvent) {
//        //Get all values using compoenet binding as mobNumBind
//        if (mobNumBind.getValue() != null && nameBind.getValue() != null &&
//            salaryBind.getValue() !=
//            null) {
//            // Add new Record in List
//            personList.add(new PersonBean(nameBind.getValue().toString(), mobNumBind.getValue().toString(),
//                                          Integer.parseInt(salaryBind.getValue().toString())));
//           
//                                                 
//        }
//    }
//
//    public void setNameBind(RichInputText nameBind) {
//        this.nameBind = nameBind;
//    }
//
//    public RichInputText getNameBind() {
//        return nameBind;
//    }
//
//    public void setMobNumBind(RichInputText mobNumBind) {
//        this.mobNumBind = mobNumBind;
//    }
//
//    public RichInputText getMobNumBind() {
//        return mobNumBind;
//    }
//
//    public void setSalaryBind(RichInputText salaryBind) {
//        this.salaryBind = salaryBind;
//    }
//
//    public RichInputText getSalaryBind() {
//        return salaryBind;
//    }
}
