package view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import model.CorporateVOImpl;

import oracle.jbo.ApplicationModule;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;
import oracle.jbo.domain.BlobDomain;

import org.apache.commons.io.IOUtils;


public class ImageServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }//init
    
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);

        //get the id parameter passed in the request
        String imageId = request.getParameter("id");
        OutputStream os = response.getOutputStream();
       
        //if id starts with temp, get the image from temporary directory
        if(imageId.startsWith("temp")){
                                           
            // get the applications temporary directory               
            File tempDir = new File(getServletContext().getInitParameter("org.apache.myfaces.trinidad.UPLOAD_TEMP_DIR"));
            //create the temporary file (remove "temp" from id)
            File tempFile = new File(tempDir, imageId.substring(4));
            FileInputStream fis = new FileInputStream(tempFile);
            //create blob from the image file
            BlobDomain blob = createBlobDomain(fis);  
           
            BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
            int b;
            byte[] buffer = new byte[10240];
            while ((b = in.read(buffer, 0, 10240)) != -1) {
                os.write(buffer, 0, b);
            }
            os.close();         
        }
        else{
            //get image from VO
            getImageFromVO(request, os);
            
            //OR get image from DB
            //getImageFromDB(request, os);
            
        }   
    }
    
    
    private void getImageFromVO(HttpServletRequest request,
                      OutputStream os){
        String appModuleName = "model.AppModule";
        String appModuleConfig = "AppModuleLocal";
        ApplicationModule am = null;
        ViewObject vo = null;
        try{
            am = Configuration.createRootApplicationModule(appModuleName, appModuleConfig);
            vo = am.findViewObject("CorporateVO");
            if (vo == null){
                throw new Exception("ImagesVO not found!");
            }
            CorporateVOImpl imageView = (CorporateVOImpl) vo;
            
            // get parameter from request
            Map paramMap = request.getParameterMap();
            oracle.jbo.domain.Number id = null;
            if (paramMap.containsKey("id")){
                String[] pVal = (String[]) paramMap.get("id");
                id = new oracle.jbo.domain.Number(pVal[0]);
            }                
            
            BlobDomain image = null;
            
            //query for the id param
            imageView.applyViewCriteria(imageView.getViewCriteria("getImageById"));
            imageView.setNamedWhereClauseParam("id", id); 
            imageView.executeQuery();
            
            // Check if a row has been found
            if(imageView.getRowCount()>0){
                // We assume the Blob to be the first field
                image = (BlobDomain)(imageView.first().getAttribute("ImageBlob"));
            }
            else{
                //No row found to get image from.
            }
            
            OutputStream outputStream = os;
            IOUtils.copy(image.getInputStream(), outputStream);
            // close the blob to release the resources
            image.closeInputStream();
        }
        catch (Exception e){
            System.out.println( e.getMessage());
        }
        finally{
            if (am != null){
                //Release the appModule
                Configuration.releaseRootApplicationModule(am, true);
            }
        }        
    }
    
    private void getImageFromDB(HttpServletRequest request, OutputStream os){
        Connection conn = null;
        String imageId = request.getParameter("id");
        try {
            //make connection to DB
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ApplicationDBDS");
            conn = ds.getConnection();
            
            PreparedStatement statement =
                conn.prepareStatement("SELECT image_id, image_blob " + "FROM images_tbl " + "WHERE image_id = ?");
            //apply servlets id parameter to query.
            statement.setLong(1, new Long(imageId));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Blob blob = rs.getBlob("IMAGE_BLOB");
                BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
                int idx;
                byte[] buffer = new byte[10240];
                while ((idx = in.read(buffer, 0, 10240)) != -1) {
                    os.write(buffer, 0, idx);
                }
                os.close();

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("SQLException error");
            }
        }
    }
    
    private BlobDomain createBlobDomain(FileInputStream file) {

        InputStream in = null;
        BlobDomain blobDomain = null;
        OutputStream out = null;

        try {
            in = file;
            blobDomain = new BlobDomain();
        
            out = blobDomain.getBinaryOutputStream();
        
            byte[] buffer = new byte[8192];
            int byteRead = 0;

            while ((byteRead = in.read(buffer, 0, 8192)) != -1) {
                out.write(buffer, 0, byteRead);
            }

            in.close();

         } catch (IOException e) {
            e.printStackTrace();
         } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return blobDomain;
    }
    
    public static Connection getOracleConnection() throws Exception {
      //setup your database connection
      String driver = "oracle.jdbc.driver.OracleDriver";
      String url = "jdbc:oracle:orcl:@localhost:1521:orcl";
      String username = "perfit";
      String password = "getitdone5299";

      Class.forName(driver); // load Oracle driver
      Connection conn = DriverManager.getConnection(url, username, password);
      return conn;
    }
}//ImageServlet
