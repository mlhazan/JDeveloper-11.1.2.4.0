package project1;

import java.util.*;
public class Service {
   
   // Singleton Collection 
   static ArrayList<Emp> loe = new ArrayList<Emp>();
  
    public Service() {
        super();
     }

    //Create
    public void addEmp(int id, int exp, String name, int sal) {
        
        Emp e = newEmp(exp,name,sal,id);
        loe.add(e);            
    }
    
    //Read
    public  ArrayList<Emp> getEmps() {
       
        return loe;
        
    }
    //Update
    public void upadteEmp(int delId, int id, int exp, String name, int sal) {

      deleteEmp(delId);
      addEmp(id,exp,name,sal);
    }
    
    //Delete
    public void deleteEmp(int id) {
       loe.remove(getObj(id));
       
    }
    
        
    //Helper Methods

    Emp newEmp(int exp, String name, int sal, int id){
        return new Emp(name,id,exp,sal);
    }    
    
    public Emp getObj(int id) {
        Iterator i = loe.iterator();
        while(i.hasNext()){
        Emp emp = (Emp)i.next();
        if((emp.getId())==id)
        {
            System.out.println(emp.getName());
            return emp;
        }
        } 
        return null;
    }
    
    

}
