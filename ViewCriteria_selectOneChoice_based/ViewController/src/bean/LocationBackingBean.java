package bean;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

public class LocationBackingBean {
    LocationPageFlow locationPageFlow;
    public LocationBackingBean() {
        super();
    }

    public void changeLocation(ValueChangeEvent vce) {
        System.err.println("New Value is-" + vce.getNewValue());
              if (vce.getNewValue() != null) {
                  this.setvalueToExpression("#{bindings.IdLOV.inputValue}",
                                            vce.getNewValue()); //Updating Model Values
                  Integer selectedCode =
                      Integer.parseInt(this.getValueFrmExpression("#{bindings.IdLOV.attributeValue}").toString());

                  System.err.println("******** Selected Value in List***** " + selectedCode);
                  System.err.println("*******Display Value in List ****" +
                                     getValueFrmExpression("#{bindings.IdLOV.selectedValue.attributeValues[1]}"));

              }
    }
    public void setvalueToExpression(String el, Object val) {
           FacesContext facesContext = FacesContext.getCurrentInstance();
           ELContext elContext = facesContext.getELContext();
           ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
           ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
           exp.setValue(elContext, val);
       }

       /**Method to get value from Expression (EL)
        * @param data
        * @return
        */
       public String getValueFrmExpression(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           String Message = null;
           Object obj = valueExp.getValue(elContext);
           if (obj != null) {
               Message = obj.toString();
           }
           return Message;
       }
}
