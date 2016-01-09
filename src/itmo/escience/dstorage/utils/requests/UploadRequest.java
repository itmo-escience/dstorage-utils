package itmo.escience.dstorage.utils.requests;



import itmo.escience.dstorage.utils.enums.StorageActionType;
import itmo.escience.dstorage.utils.enums.StorageMethod;
import itmo.escience.dstorage.utils.enums.StorageObjectType;
import org.json.simple.JSONObject;

public class UploadRequest extends Request{
    private String tag=null;
    private String name;
    private long filesize;
    private int reserv=-2;
    private int level=-2;
    public UploadRequest(){
        super();
        action=StorageActionType.PUT;
        type=StorageObjectType.f;
        method=StorageMethod.PUT;
    }
    //TODO builder
    public void setTag(String tag){
        this.tag=tag;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setReserv(int res){
        this.reserv=res;
    }
    public void setFileSize(long size){
        this.filesize=size;
    }
    public void setStorageLevel(int lvl){
        this.level=lvl;
    }
    @Override
    public JSONObject getRequestJson(){
        JSONObject json= super.getRequestJson();
        if (reserv!=-2) json.put("reserv", reserv);
        if (tag!=null) json.put("tag", tag);
        if (level!=-2) json.put("lvl", level);
        json.put("name", name);
        json.put("file_size", filesize);
        return json;
    }   
}