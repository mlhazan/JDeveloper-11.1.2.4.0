package view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;

public class RichBean {
    public RichBean() {
    }
    RichInputText itx;
    RichInputText ity;
    RichInputText itz;

    public void calculateNumbers(ActionEvent actionEvent) {
        int a = Integer.parseInt(this.getItx().getValue().toString());
        int b = Integer.parseInt(this.getIty().getValue().toString());
        this.getItz().setValue(a+b);
    }

    public void setItx(RichInputText itx) {
        this.itx = itx;
    }

    public RichInputText getItx() {
        return itx;
    }

    public void setIty(RichInputText ity) {
        this.ity = ity;
    }

    public RichInputText getIty() {
        return ity;
    }

    public void setItz(RichInputText itz) {
        this.itz = itz;
    }

    public RichInputText getItz() {
        return itz;
    }
}
