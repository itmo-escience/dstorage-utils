package itmo.escience.dstorage.utils.requests;



import itmo.escience.dstorage.utils.enums.StorageActionType;
import itmo.escience.dstorage.utils.enums.StorageMethod;
import org.json.simple.JSONObject;

/**
 *
 * @author Anton Spivak
 */
public class MetadataRequest extends Request{
    private String name;
    private int version=-1;
    public MetadataRequest(){
        super();
        action=StorageActionType.GET_METADATA;
        method=StorageMethod.PUT;
    }
    //TODO builder
    public void setName(String name){
        this.name=name;
    }
    public void setVersion(int ver){
        this.version=ver;
    }
    @Override
    public JSONObject getRequestJson(){
        JSONObject json= super.getRequestJson();
        //json.remove("type");
        json.put("name", name);
        json.put("version", Long.toString(version));
        return json;
    }   
}
