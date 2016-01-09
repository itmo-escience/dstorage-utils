package itmo.escience.dstorage.utils.agent;



import itmo.escience.dstorage.utils.Storage;
import itmo.escience.dstorage.utils.enums.StorageActionType;
import itmo.escience.dstorage.utils.enums.StorageMethod;
import itmo.escience.dstorage.utils.network.HttpConn;
import itmo.escience.dstorage.utils.responses.UploadResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.entity.InputStreamEntity;

/**
 *
 * @author Anton Spivak
 */
public class AgentRequestUpload {
    //private String uri;
    private String obj_id;
    private StorageMethod method=StorageMethod.PUT;
    private String sign;
    private String ticket;
    private FileInputStream fis;
    private StorageActionType type=StorageActionType.AGENTPUT;
    private String host;
    private int port;
    private int storageLevel=-2;
    private long cmdid=-1;
    public AgentRequestUpload(String uri, String obj_id){
        setUri(uri);
        this.obj_id=obj_id;               
    }    
    public AgentRequestUpload(UploadResponse response){
        System.out.println("Response:"+response.toString());
        obj_id=response.getObj();
        setUri(response.getAgentUri());
        sign=response.getSign();
        ticket=response.getTicket();
        cmdid=response.getCmdID();
    }
    public void setUri(String uri){
        parseUri(uri);
    }
    public void setStorageLevel(int lvl){
        this.storageLevel=lvl;
    }
    public void setStream(FileInputStream fis){
        this.fis=fis;
    }
    private void parseUri(String uri){
        final URL urlAgent;
        try {
            urlAgent = new URL (uri);
            this.host=urlAgent.getHost();
            this.port =urlAgent.getPort();
        } catch (MalformedURLException ex) {
            Logger.getLogger(AgentRequestUpload.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }                
    }
    
    public AgentResponse execute(){
        AgentResponse response=null;
        HttpConn httpAgent = new HttpConn();
        try{            
            httpAgent.setup(host,Integer.toString(port)); 
            httpAgent.setMethod(method,obj_id);
            httpAgent.setEntity((new InputStreamEntity(fis, -1)));
            httpAgent.setHeader("Ticket", ticket);
            httpAgent.setHeader("Sign", sign);
            if(this.storageLevel!=-2)httpAgent.setHeader("StorageLevel", Integer.toString(this.storageLevel));
            if(this.cmdid!=-1)httpAgent.setHeader("CmdID", Long.toString(this.cmdid));
            httpAgent.connect();
            response=new AgentResponse(httpAgent.getHttpResponse());
            httpAgent.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return response;
    }    
    public StorageActionType getType(){return type;}
}
