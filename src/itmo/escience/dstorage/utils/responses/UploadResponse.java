package itmo.escience.dstorage.utils.responses;



import itmo.escience.dstorage.utils.enums.StorageActionType;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

/**
 *
 * @author Anton Spivak
 */
public class UploadResponse extends Response {
    private String agentUri;
    private String obj;
    private String sign;
    private String ticket;
    private long cmdid;
    
    public UploadResponse(HttpResponse response){
        super(response);
        type=StorageActionType.PUT;
        continueParseFromJson();

    }
    public String getAgentUri(){return agentUri;}
    public String getObj(){return obj;}
    public String getSign(){return sign;}
    public String getTicket(){return ticket;}
    public long getCmdID(){return cmdid;}
    protected void continueParseFromJson(){        
        Iterator<?> it=json.keySet().iterator();
        while(it.hasNext()){
            String key=(String)it.next();
            switch(key.toUpperCase()){
                case "SIGN": 
                    sign=(String)json.get(key);
                    break;
                case "TICKET": 
                    ticket=(String)json.get(key);
                    break;
                case "OBJ_ID": 
                    obj=(String)json.get(key);
                    break;
                case "AGENT_URI": 
                    agentUri=(String)json.get(key);
                    break;
                case "CMDID":
                    cmdid=(Long)json.get(key);
                    break;
            }
        }        
    }
    
    
    @Override
    public String toString(){
        return "STATUS:"+status.toString()+
                " OBJ_ID:"+obj+
                " AGENT_URI:"+agentUri;
    }
}
