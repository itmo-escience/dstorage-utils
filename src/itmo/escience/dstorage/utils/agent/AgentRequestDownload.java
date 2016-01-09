package itmo.escience.dstorage.utils.agent;

import itmo.escience.dstorage.utils.Storage;
import itmo.escience.dstorage.utils.enums.StorageActionType;
import itmo.escience.dstorage.utils.enums.StorageMethod;
import itmo.escience.dstorage.utils.network.HttpConn;
import itmo.escience.dstorage.utils.responses.DownloadResponse;
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
public class AgentRequestDownload {
    //private String uri;
    private String obj_id;
    private StorageMethod method=StorageMethod.GET;
    private String sign;
    private String ticket;
    private FileInputStream fis;
    private StorageActionType type=StorageActionType.AGENTGET;
    private String host;
    private int port;
    private int storageLevel=-2;
    private InputStream fileInputStream;
    public AgentRequestDownload(String uri, String obj_id){
        setUri(uri);
        this.obj_id=obj_id;               
    }    
    public AgentRequestDownload(DownloadResponse response){
        Logger.getLogger(AgentRequestDownload.class.getName()).info("Response:"+response.toString());
        obj_id=response.getObj();
        setUri(response.getAgentUri());
        sign=response.getSign();
        ticket=response.getTicket();
        storageLevel=response.getLevel();        
    }
    public void setUri(String uri){
        parseUri(uri);
    }
    public void setStream(FileInputStream fis){
        this.fis=fis;
    }
    public void setStorageLevel(int lvl){
        this.storageLevel=lvl;
    }
    private void parseUri(String uri){
        final URL urlAgent;
        try {
            urlAgent = new URL (uri);
            this.host=urlAgent.getHost();
            this.port =urlAgent.getPort();
        } catch (MalformedURLException ex) {
            Logger.getLogger(AgentRequestDownload.class.getName()).log(Level.SEVERE, null, ex);
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
            httpAgent.connect();
            this.fileInputStream=httpAgent.getStreamResponse();
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
    public InputStream getFileStream(){return fileInputStream;}
}
