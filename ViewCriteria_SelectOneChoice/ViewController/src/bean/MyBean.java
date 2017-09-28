package bean;

import java.util.Map;

import javax.faces.event.ActionEvent;


import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

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
        JUCtrlListBinding lst = (JUCtrlListBinding)bc.get("Name1");
        ViewRowImpl r = (ViewRowImpl)lst.getSelectedValue();
        String na = (String)r.getAttribute("Name1");
        Map m = ob.getParamsMap();
        m.put("n",na);
        ob.execute();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
