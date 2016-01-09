package itmo.escience.dstorage.utils.requests;



import itmo.escience.dstorage.utils.enums.StorageActionType;
import itmo.escience.dstorage.utils.enums.StorageMethod;
import itmo.escience.dstorage.utils.enums.StorageObjectType;
import org.json.simple.JSONObject;

public class DownloadRequest extends Request{
    private String name;
    private int level=-2;
    public DownloadRequest(){
        super();
        action=StorageActionType.GET;
        type=StorageObjectType.f;
        method=StorageMethod.GET;
    }
    //TODO builder
    public void setName(String name){
        this.name=name;
    }
    public void setStorageLevel(int lvl){
        this.level=lvl;
    }
    @Override
    public JSONObject getRequestJson(){
        JSONObject json= super.getRequestJson();
        if (level!=-2) json.put("lvl", level);
        json.put("name", name);
        return json;
    }   
}