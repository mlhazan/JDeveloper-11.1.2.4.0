package model;

import oracle.jbo.ViewCriteria;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Sep 19 23:11:20 ADT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LocationLOVImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public LocationLOVImpl() {
    }

    /**
     * Returns the variable value for name.
     * @return variable value for name
     */
    public String getName1() {
        return (String)ensureVariableManager().getVariableValue("Name1");
    }

    /**
     * Sets <code>value</code> for variable name.
     * @param value value to bind as name
     */
    public void setName1(String value) {
        ensureVariableManager().setVariableValue("Name1", value);
    }

    /**
     * Returns the variable value for LocId.
     * @return variable value for LocId
     */
    public Number getLocId() {
        return (Number)ensureVariableManager().getVariableValue("LocId");
    }

    /**
     * Sets <code>value</code> for variable LocId.
     * @param value value to bind as LocId
     */
    public void setLocId(Number value) {
        ensureVariableManager().setVariableValue("LocId", value);
    }
    
    public void setViewCriteria(String name, Number locId){
        ViewCriteria vc = getViewCriteria("LocationLOVCriteria");
        vc.resetCriteria();
        setName1(name);
        setLocId(locId);
        applyViewCriteria(vc);
        executeQuery();
    }
}
