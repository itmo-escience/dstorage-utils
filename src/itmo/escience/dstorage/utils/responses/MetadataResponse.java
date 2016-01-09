package itmo.escience.dstorage.utils.responses;



import itmo.escience.dstorage.utils.enums.StorageActionType;
import itmo.escience.dstorage.utils.obj.ObjectMetaData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class MetadataResponse extends Response {
    private String msg;
    private List<ObjectMetaData> metadata=new ArrayList();
    public MetadataResponse(HttpResponse response){
        super(response);
        type=StorageActionType.GET_METADATA;
        httpCode=response.getStatusLine().getStatusCode();
        continueParseFromJson();
    }
    public List<ObjectMetaData> getMetaData(){return metadata;}
    /*
    {"msg":"Check list for info","list":[{"Path":"\/24","Comment":"","Owner":"Admin","Modified":"Admin","obj_id":
    "565381a81e1f685381238a4c","Size":6698,"Time":"01:14:16","Creator":"Admin","BkpLvl":3,"RawTime":1448313256221,
    "Date":"2015.11.24","Name":"\/24\/0101.txt","Type":"f",
    "Descendants":0,"Version":0,"State":1,"AgentIP":{"192_168_18_103":[0]},"DefaultCT":"text\/plain"}],"status":"OK"}
    
    {"msg":"Check list for info","list":[{"Path":"\/","Owner":"Admin","Modified":"","obj_id":"564c8828cb1f9676957f9ed6","Size":0,
    "Time":"18:16:08","Creator":"Admin",
    "Date":"18.11.2015","Name":"\/8","Type":"d","Version":0,"State":1,"DefaultCT":"application\/octet-stream"}],"status":"OK"
    */    
    
    protected void continueParseFromJson(){ 
        System.out.println("metadata json:"+json);
        Iterator<?> it=json.keySet().iterator();
        while(it.hasNext()){
            String key=(String)it.next();
            switch(key.toUpperCase()){
                case "LIST": 
                    metadata.addAll(ObjectMetaData.parse(json));
                    break;    
            }
        }        
    }
    @Override
    public String toString(){
        return "STATUS:"+status.toString()+
                " MSG:"+msg;
    }
}
