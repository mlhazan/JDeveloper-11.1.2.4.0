package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.output.RichImage;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.binding.BindingContainer;
import oracle.jbo.Row;
import oracle.jbo.domain.BlobDomain;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.UploadedFile;

public class ImageRepoBean {
    private UploadedFile myImageFile;
    private RichImage _previewImage;
    private RichPanelFormLayout mainForm;

    public ImageRepoBean() {
        super();
    }//constructor

    //Accessors
    public UploadedFile getImageFile() { return myImageFile; }
    public void setImageFile(UploadedFile f) { myImageFile = f; }
    public void setPreviewImage(RichImage i) { _previewImage = i; }
    public RichImage getPreviewImage() { return _previewImage; }
    public void setMainForm(RichPanelFormLayout mainForm) {  this.mainForm = mainForm;  }
    public RichPanelFormLayout getMainForm() {   return mainForm;  }

    //Upload image to database invoked from Ok on popup
    public void uploadImageToDB(DialogEvent dialogEvent) {
        //get bindings
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        DCBindingContainer bindingsImpl = (DCBindingContainer)bindings;
        DCIteratorBinding itr = bindingsImpl.findIteratorBinding("CorporateVOIterator");
        //get current row
        Row row = itr.getCurrentRow();
        //get the pre-defined temporary upload directory, set in web.xml
        String fileUploadLoc =
            FacesContext.getCurrentInstance().getExternalContext().getInitParameter("org.apache.myfaces.trinidad.UPLOAD_TEMP_DIR");
        File destinationDir = new File(fileUploadLoc);
        String filename = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("path");
        //get the image from temporary directory
        File imageFile = new File(destinationDir, filename);
        FileInputStream is = null;
        try{
            is = new FileInputStream(imageFile);
        }
        catch (FileNotFoundException e){
            System.out.println(e);
        }
        
        //create blob from file and save to DB
        BlobDomain blob = _createBlobDomain(is);
        row.setAttribute("ImageBlob", blob);
        bindings.getOperationBinding("Commit").execute();

        //delete the temporarily uploaded file
        imageFile.delete();       
        RequestContext.getCurrentInstance().addPartialTarget(mainForm);
    }//uploadImageToDB

    private BlobDomain _createBlobDomain(FileInputStream is) {
        //create blob from fileinputstream
        InputStream in = null;
        BlobDomain blobDomain = null;
        OutputStream out = null;

        try {
            in = is;
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
    
    public void handleChangeFile(ValueChangeEvent valueChangeEvent) {
        //upload file to temp. directory
        File file = handleUpload((UploadedFile)valueChangeEvent.getNewValue());
        //get the uploaded files path
        String path = file.getPath();
        path = path.substring(64);
        
        //save new path to pageflowscope var used as servlet param.
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("path", path);
        //display preview image
        getPreviewImage().setRendered(true);
        RequestContext.getCurrentInstance().addPartialTarget(_previewImage.getParent());      
    }
            
    public File handleUpload(UploadedFile myImageFile) {
        //get the temporary upload directory
        String fileUploadLoc =
            FacesContext.getCurrentInstance().getExternalContext().getInitParameter("org.apache.myfaces.trinidad.UPLOAD_TEMP_DIR");

        File destinationDir = new File(fileUploadLoc);
        boolean exists = destinationDir.exists();
        // Create upload directory if it does not exist.
        if (!exists) {
            destinationDir.mkdirs();
        }
        
        //create the file for your image file
        File destinationFile = new File(fileUploadLoc, myImageFile.getFilename());
        
        try {
            //copy contents to the file
            this.copy(myImageFile.getInputStream(),
                      new FileOutputStream(destinationFile)); 

        } catch (IOException e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return destinationFile;
    }
    
    public void copy(InputStream in, OutputStream out) {
        try {
            HttpSession session =
                (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);

            int bytes = -1;
            while ((bytes = in.read()) > -1 &&
                   session.getAttribute("uploadCanceled") == null) {
                out.write(bytes);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleCancelPop(PopupCanceledEvent popupCanceledEvent) {
        //hide selected image to prevent it from displaying when popup reopened.
        getPreviewImage().setRendered(false);
        String filename = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("path");
        if (filename !=null){//if a path has been selected
            //get the image that was uploaded
            String fileUploadLoc =
                    FacesContext.getCurrentInstance().getExternalContext().getInitParameter("org.apache.myfaces.trinidad.UPLOAD_TEMP_DIR");
            File destinationDir = new File(fileUploadLoc);
            File imageFile = new File(destinationDir, filename);
            boolean exists = imageFile.exists();
            // if it exists, delete it.
            if (exists) {
                //delete image from temp directory       
                imageFile.delete();
            }
        }
    }

    public void handleShowPop(PopupFetchEvent popupFetchEvent) {
        //hide previously selected image for upload
        _previewImage.setRendered(false);
    }
}//ImageRepoBean
