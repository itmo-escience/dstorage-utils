package itmo.escience.dstorage.utils.requests;



import itmo.escience.dstorage.utils.enums.StorageActionType;
import itmo.escience.dstorage.utils.enums.StorageMethod;
import itmo.escience.dstorage.utils.enums.StorageObjectType;
import itmo.escience.dstorage.utils.tools.JSONUtils;
import org.json.simple.JSONObject;

public class Request {
    public StorageObjectType type;
    public String objID;
    public JSONObject json;
    public String tag=null;
    //public int reserv=-2;
    public StorageActionType action;
    public StorageMethod method;
    public String secid;
    public Request(){
        this.json= new JSONObject();
    }
    public Request(String str){
        //TODO call Request()
        //JSONObject json= new JSONObject();
        this.json=JSONUtils.parseJSON(str);
    }
    public JSONObject getRequestJson(){
        //JSONObject j=new JSONObject();
        //if (!tag.equals(null))json.put("tag", tag);
        //if (reserv!=-2) json.put("reserv", reserv);
        //System.out.println("getRequestJson="+json.toString());
        if (action!=null) 
            json.put("action",action.toString());
        if (type!=null) 
            json.put("type",type.toString());
            json.put("secid",secid);
        return this.json;
    }
    public StorageActionType getActionType(){return action;}
}
