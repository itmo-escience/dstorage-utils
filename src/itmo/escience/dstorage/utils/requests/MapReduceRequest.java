package itmo.escience.dstorage.utils.requests;



import itmo.escience.dstorage.utils.enums.StorageActionType;
import itmo.escience.dstorage.utils.enums.StorageMethod;
import itmo.escience.dstorage.utils.enums.StorageObjectType;
import org.json.simple.JSONObject;

public class MapReduceRequest extends Request{
    private String tag;
    private String name;
    public MapReduceRequest(){
        super();
        action=StorageActionType.PUT;
        type=StorageObjectType.mr;
        method=StorageMethod.PUT;
    }
    public void setTag(String tag){
        this.tag=tag;
    }
    public void setName(String name){
        this.name=name;
    }
    @Override
    public JSONObject getRequestJson(){
        JSONObject json= super.getRequestJson();
        json.put("tag", tag);
        json.put("name", name);
        return json;
    }
    
}
