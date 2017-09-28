package bean;

import java.util.Map;

import javax.faces.event.ActionEvent;


import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class MyBean {
    private String name;
    public MyBean() {
    }
    public BindingContainer getContainer(){
        return BindingContext.getCurrent().getCurrentBindingsEntry();
        
    }
    public void getNameList(ActionEvent actionEvent) {
        BindingContainer bc = this.getContainer();
        OperationBinding ob = bc.getOperationBinding("applyFindByName");
        Map m = ob.getParamsMap();
        m.put("n",name);
        ob.execute();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
