package itmo.escience.dstorage.utils.requests;



import itmo.escience.dstorage.utils.enums.StorageActionType;
import itmo.escience.dstorage.utils.enums.StorageLevel;
import itmo.escience.dstorage.utils.enums.StorageMethod;
import itmo.escience.dstorage.utils.enums.StorageObjectType;
import org.json.simple.JSONObject;

public class MoveFileRequest extends Request{
    private String tag;
    private String name;
    private String fromHost;
    private String toHost;
    private StorageLevel lvlfrom;
    private StorageLevel lvlto;    
    //lvlfrom, lvlto, from, to
    public MoveFileRequest(){
        super();
        action=StorageActionType.MVFILE;
        type=StorageObjectType.f;
        method=StorageMethod.PUT;
    }
    public void setLvlFrom(StorageLevel lvl){lvlfrom=lvl;}
    public void setLvlTo(StorageLevel lvl){lvlto=lvl;}
    public void setFromHost(String h){fromHost=h;}
    public void setToHost(String h){toHost=h;}
    public void setName(String name){this.name=name;}
    @Override
    public JSONObject getRequestJson(){
        JSONObject json= super.getRequestJson();
        json.put("lvlto", lvlto.getNum());
        json.put("lvlfrom", lvlfrom.getNum());
        json.put("from", fromHost);
        json.put("to", toHost);
        json.put("name", name);
        return json;
    }
    
}
