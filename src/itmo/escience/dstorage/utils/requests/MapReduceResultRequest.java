package itmo.escience.dstorage.utils.requests;



import itmo.escience.dstorage.utils.enums.StorageActionType;
import itmo.escience.dstorage.utils.enums.StorageMethod;
import itmo.escience.dstorage.utils.enums.StorageObjectType;
import org.json.simple.JSONObject;


public class MapReduceResultRequest extends Request{
    private String tag;
    private String name;
    public MapReduceResultRequest(){
        super();
        action=StorageActionType.GET;
        type=StorageObjectType.mr;
        method=StorageMethod.PUT;
    }
    public void setName(String name){
        this.name=name;
    }
    @Override
    public JSONObject getRequestJson(){
        JSONObject json= super.getRequestJson();
        json.put("name", name);
        return json;
    }
    
}