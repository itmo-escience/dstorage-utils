package itmo.escience.dstorage.utils.agent;



import itmo.escience.dstorage.utils.enums.ResponseStatus;
import itmo.escience.dstorage.utils.enums.StorageActionType;
import itmo.escience.dstorage.utils.responses.Response;
import itmo.escience.dstorage.utils.tools.JSONUtils;
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
 * @author anton
 */
public class AgentResponse {
    protected StorageActionType type=StorageActionType.AGENTPUT;
    protected int httpCode=HttpStatus.SC_BAD_REQUEST;
    protected ResponseStatus status=ResponseStatus.Error;
    protected String msg;    
    protected JSONObject json;

    public AgentResponse(HttpResponse response){
        httpCode=response.getStatusLine().getStatusCode();        
        if(httpCode>200){
            try {
                parseFromJson(EntityUtils.toString(response.getEntity()));
            } catch (IOException ex) {
                Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(Response.class.getName()).log(Level.SEVERE, null, ex);
            }        
        }
        else {
            status=ResponseStatus.OK;
        }
    }
    public String getMsg(){return msg;}
    public int getHttpCode(){return httpCode;}
    public ResponseStatus getResponseStatus(){return status;}
    protected void parseFromJson(String st){
        json=JSONUtils.parseJSON(st);
        Iterator<?> it=json.keySet().iterator();
        while(it.hasNext()){
            String key=(String)it.next();
            switch(key.toUpperCase()){
                case "STATUS": 
                    try{
                        status=ResponseStatus.valueOf((String)json.get(key));
                    }catch(Exception e){
                        System.out.println("Unknown status in response:"+json.get(key));
                    }
                    break;
                case "MSG": 
                    msg=(String)json.get(key);
                    break;
            }
        }        
    }
    public boolean getStatus(){
        return status.equals(ResponseStatus.OK)?true:false;
    }
    public StorageActionType getType(){return type;}
    @Override
    public String toString(){
        if(httpCode>200)
            return "Status:"+status.toString()+" msg:"+msg+" httpCode:"+httpCode;
        else
            return "httpCode:"+httpCode;
    }
}
