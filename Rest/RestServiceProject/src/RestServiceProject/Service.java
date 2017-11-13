package RestServiceProject;

import java.util.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/restlab")
public class Service {
   
   // Singleton Collection 
   static ArrayList<Emp> loe = new ArrayList<Emp>();
  
    public Service() {
        super();
     }

    //Create
    public void addEmp(@QueryParam("id") int id, @QueryParam("exp")int exp, @QueryParam("name") String name, @QueryParam("sal")int sal) {
        
        Emp e = newEmp(exp,name,sal,id);
        loe.add(e);            
    }
    @GET
    @Produces("application/xml")
    //Read
    public  ArrayList<Emp> getEmps() {
       
        return loe;
        
    }
    //Update
    public void upadteEmp(int delId, int id, int exp, String name, int sal) {

      deleteEmp(delId);
      addEmp(id,exp,name,sal);
    }
    @DELETE
    //Delete
    public void deleteEmp(@QueryParam("id") int id) {
       loe.remove(getObj(id));
       
    }
    
        
    //Helper Methods
    
    @PUT
        @Path("defaultEmp")
        public Response addEmp(){
            loe.add(new Emp("Adderley",1,5,1000));
            loe.add(new Emp("Coltrane",2,6,2000));
            loe.add(new Emp("Davis",3,7,3000));
            return Response.ok().build();
    }

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
