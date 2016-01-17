package itmo.escience.dstorage.utils.obj;



import itmo.escience.dstorage.utils.enums.StorageObjectType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Anton Spivak
 */
public class ObjectMetaData {
    public static String DATEFORMAT="yyyy.MM.dd";
    private StorageObjectType type;
    private String comment;
    private String path;
    private String owner;
    private String modified;
    private String id;
    private long size;
    private String time;
    private String creator;
    private long backupLevel;
    private long rawtime;
    private String date;
    private String name;
    private long descendants;
    private long version;
    private long state;
    private Map<String,List<Long>> agents=new HashMap();//agents with levels 
    private String defaultCT;
    
    public void setObjType(StorageObjectType t){this.type=t;}
    public void setComment(String comm){this.comment=comm;}
    public void setPath(String p){this.path=p;}
    public void setOwner(String ow){this.owner=ow;}
    public void setModified(String mod){this.modified=mod;}
    public void setObjID(String id){this.id=id;}
    public String getObjID(){ return id;}
    public void setSize(long s){this.size=s;}
    public long getSize(){ return this.size;}
    public void setTime(String time){this.time=time;}
    public void setCreator(String creator){this.creator=creator;}
    public void setBackupLevel(long lvl){this.backupLevel=lvl;}
    public void setRawTime(long raw){this.rawtime=raw;}
    public void setDate(String d){this.date=d;}
    public void setName(String name){this.name=name;}
    public void setDescendants(long des){this.descendants=des;}
    public void setVersion(long ver){this.version=ver;}
    public void setState(long st){this.state=st;}
    public void setAgents(Map<String,List<Long>> ips){this.agents=ips;}
    public Map<String,List<Long>> getAgents(){ return this.agents; }
    public void setDefaultCT(String ct){this.defaultCT=ct;}
    
    public ObjectMetaData(){
        
    }
    public static List<ObjectMetaData> parse(JSONObject json){
        List<ObjectMetaData> result=new ArrayList();
        JSONArray jsonList = new JSONArray();
        jsonList = (JSONArray) json.get("list");
        Iterator itArray=jsonList.iterator();
        while(itArray.hasNext()){
            ObjectMetaData meta=new ObjectMetaData();
            JSONObject jo=(JSONObject)itArray.next();
            Iterator<?> it=jo.keySet().iterator();            
            while(it.hasNext()){
                String key=(String)it.next();
                switch(key.toUpperCase()){
                    case "PATH": 
                        meta.setPath((String)jo.get(key));
                        break;
                    case "TYPE": 
                        try{
                            meta.setObjType(StorageObjectType.valueOf((String)jo.get(key)));
                        }catch(Exception e){
                            System.out.println("Unknown object type in response:"+jo.get(key));
                        }
                        break;
                    case "COMMENT": 
                        meta.setComment((String)jo.get(key));                        
                        break;
                    case "OWNER": 
                        meta.setOwner((String)jo.get(key));                        
                        break;
                    case "MODIFIED": 
                        meta.setModified((String)json.get(key));                        
                        break;
                    case "OBJ_ID": 
                        meta.setObjID((String)jo.get(key));                        
                        break;
                    case "SIZE": 
                        meta.setSize((Long)jo.get(key));                        
                        break;
                    case "TIME": 
                        meta.setTime((String)jo.get(key));                        
                        break;
                    case "CREATOR": 
                        meta.setCreator((String)jo.get(key));                        
                        break;
                    case "BKPLVL": 
                        meta.setBackupLevel((Long)jo.get(key));                        
                        break;
                    case "RAWTIME": 
                        meta.setRawTime((Long)jo.get(key));                        
                        break;
                    case "DATE":
                        //NEED to fix in core date format differ for file and directory  (2015.11.24 and 18.11.2015)
                        /* 
                        try {
                            meta.setDate(new SimpleDateFormat(DATEFORMAT).parse((String)json.get(key)));
                        } catch (ParseException ex) {
                            Logger.getLogger(ObjectMetaData.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        */
                        meta.setDate((String)jo.get(key));                          
                        break;
                    case "NAME": 
                        meta.setName((String)jo.get(key));                        
                        break;
                    case "DESCENDANTS": 
                        meta.setDescendants((Long)jo.get(key));                        
                        break;
                    case "VERSION": 
                        meta.setVersion((Long)jo.get(key));                        
                        break;
                    case "STATE": 
                        meta.setState((Long)jo.get(key));                        
                        break;
                    case "AGENTIP": 
                        JSONObject agentList = new JSONObject();                        
                        agentList = (JSONObject) jo.get(key);
                        if(agentList==null)break;
                        Iterator itAgents=agentList.keySet().iterator(); 
                        Map<String,List<Long>> lAgents=new HashMap();
                        while(itAgents.hasNext()){
                            String ja=(String)itAgents.next();
                            JSONArray levels=(JSONArray)agentList.get(ja);
                            Iterator<?> itLvl=levels.iterator();
                            List<Long> agLevels=new ArrayList();
                            while(itLvl.hasNext()){
                                long k=(Long)itLvl.next();
                                agLevels.add(k);
                            }
                            lAgents.put(ja, agLevels);
                        }                       
                        meta.setAgents(lAgents);                        
                        break;
                    case "DEFAULTCT": 
                        meta.setDefaultCT((String)json.get(key));
                        break;
                }
            }
            result.add(meta);
        }        
        return result;
    }
    /*
    Example of output here
    {"msg":"Check list for info","list":[{"Path":"\/24","Comment":"","Owner":"Admin","Modified":"Admin","obj_id":
    "565381a81e1f685381238a4c","Size":6698,"Time":"01:14:16","Creator":"Admin","BkpLvl":3,"RawTime":1448313256221,
    "Date":"2015.11.24","Name":"\/24\/0101.txt","Type":"f",   
    "Descendants":0,"Version":0,"State":1,"AgentIP":{"192_168_18_103":[0]},"DefaultCT":"text\/plain"}],"status":"OK"}
    
    {"msg":"Check list for info","list":[{"Path":"\/8","Comment":"","Owner":"Admin","Modified":"Admin","obj_id":
    "564c8ecccb1f9676957f9ef2","Size":6698,"Time":"18:44:28","Creator":"Admin","BkpLvl":3,"RawTime":1447857868678,"Date":"2015.11.18","Name":"\/8\/0101.txt",
    "Type":"f","Descendants":0,"Version":0,"State":1,"AgentIP":[{"127_0_0_1":0}],"DefaultCT":"text\/plain"}],"status":"OK"}
    
    */
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("name:"+name);
        sb.append(" path:"+path);
        sb.append(" agents:"+agents.toString());
        return sb.toString();
    }
}
