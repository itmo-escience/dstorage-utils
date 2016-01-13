package itmo.escience.dstorage.utils.requests;



import itmo.escience.dstorage.utils.enums.StorageActionType;
import itmo.escience.dstorage.utils.enums.StorageLevel;
import itmo.escience.dstorage.utils.enums.StorageMethod;
import itmo.escience.dstorage.utils.enums.StorageObjectType;
import org.json.simple.JSONObject;

public class MoveLevelRequest extends Request{
    private String name;
    private String host;
    private StorageLevel lvlfrom;
    private StorageLevel lvlto;
    // (action = put, type = f, host = ip, lvlfrom и lvlto
    public MoveLevelRequest(){
        super();
        action=StorageActionType.MVLVL;
        type=StorageObjectType.f;
        method=StorageMethod.PUT;
    }
    public void setLvlFrom(StorageLevel lvl){lvlfrom=lvl;}
    public void setLvlTo(StorageLevel lvl){lvlto=lvl;}
    public void setHost(String h){host=h;}
    public void setName(String name){this.name=name;}
    @Override
    public JSONObject getRequestJson(){
        JSONObject json= super.getRequestJson();
        json.put("name", name);
        json.put("lvlto", lvlto.getNum());
        json.put("lvlfrom", lvlfrom.getNum());
        json.put("from", host);
        return json;
    }
    
}
