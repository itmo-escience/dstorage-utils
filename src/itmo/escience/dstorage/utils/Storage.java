package itmo.escience.dstorage.utils;

import itmo.escience.dstorage.utils.agent.AgentRequestDownload;
import itmo.escience.dstorage.utils.agent.AgentRequestUpload;
import itmo.escience.dstorage.utils.agent.AgentResponse;
import itmo.escience.dstorage.utils.enums.StorageLevel;
import itmo.escience.dstorage.utils.network.HttpConn;
import itmo.escience.dstorage.utils.requests.DownloadRequest;
import itmo.escience.dstorage.utils.requests.MetadataRequest;
import itmo.escience.dstorage.utils.requests.MoveFileRequest;
import itmo.escience.dstorage.utils.requests.MoveLevelRequest;
import itmo.escience.dstorage.utils.requests.Request;
import itmo.escience.dstorage.utils.requests.UploadRequest;
import itmo.escience.dstorage.utils.responses.DownloadResponse;
import itmo.escience.dstorage.utils.responses.MetadataResponse;
import itmo.escience.dstorage.utils.responses.Response;
import itmo.escience.dstorage.utils.responses.ResponseFactory;
import itmo.escience.dstorage.utils.responses.UploadResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
//import client.HttpConn;

public class Storage {
    private String address;
    private String port;
    private String secid;
    
    public Storage(String address,String port,String secid){
        this.address=address;
        this.port=port;
        this.secid=secid;
    }
    public String getStorageURI(){
        return address+":"+port;
    }
    public Response uploadFile(File file,String storageFilename,long size,int reserv,String tag,int level){
        //communicate with Core
        UploadRequest request=new UploadRequest();
        request.setReserv(reserv);
        request.setTag(tag);
        request.setStorageLevel(level);       
        request.setFileSize(file.length());
        request.setName(storageFilename);
        Response response=execute(request);
        if (!response.getStatus()){
            return response;
        }
        //communicate with Agent
        AgentRequestUpload agentRequest=new AgentRequestUpload((UploadResponse)response);
        FileInputStream fis=null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);            
        }
        agentRequest.setStream(fis);
        agentRequest.setStorageLevel(level);
        AgentResponse agentResponse=agentRequest.execute();            
        return new Response(agentResponse);
    }
    public Response downloadFile(String filename,String storageFilename,int level){
        //communicate with Core
        DownloadRequest request=new DownloadRequest();
        request.setStorageLevel(level);       
        request.setName(storageFilename);
        DownloadResponse response=(DownloadResponse)execute(request);
        if (!response.getStatus()){
            return response;
        }
        if(response.getLevel()!=level)
            Logger.getLogger(Storage.class.getName()).info("Level from core the different as desired. Desired:"+level+" Returned:"+response.getLevel());
        //communicate with Agent
        AgentRequestDownload agentRequest=new AgentRequestDownload((DownloadResponse)response);
        //agentRequest.setStorageLevel(resp);
        
        AgentResponse agentResponse=agentRequest.execute();
        InputStream is=agentRequest.getFileStream();
        OutputStream outStream;             
        try {
            outStream = new FileOutputStream(filename);        
            int intBytesRead=0;
            byte[] bytes = new byte[4096];
            while ((intBytesRead=is.read(bytes))!=-1)
                outStream.write(bytes,0,intBytesRead);
            outStream.flush();
            outStream.close();
            is.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        }                
        return new Response(agentResponse);
    }
    public long downloadFileAsStream(String filename,String storageFilename,int level){
        //communicate with Core
        DownloadRequest request=new DownloadRequest();
        request.setStorageLevel(level);       
        request.setName(storageFilename);
        DownloadResponse response=(DownloadResponse)execute(request);
        if (!response.getStatus()){
            return 0L;
        }
        if(response.getLevel()!=level)
            Logger.getLogger(Storage.class.getName()).info("Level from core the different as desired. Desired:"+level+" Returned:"+response.getLevel());
        //communicate with Agent
        AgentRequestDownload agentRequest=new AgentRequestDownload((DownloadResponse)response);
        //agentRequest.setStorageLevel(resp);
        long totalReaded=0L;
        AgentResponse agentResponse=agentRequest.execute();
        InputStream is=agentRequest.getFileStream();
        OutputStream outStream;             
        try {
            //outStream = new OutputStream(filename);        
            int intBytesRead=0;
            byte[] bytes = new byte[4096];
            while ((intBytesRead=is.read(bytes))!=-1)
                totalReaded+=(long)intBytesRead;
            //    outStream.write(bytes,0,intBytesRead);
            //outStream.flush();
            //outStream.close();
            is.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        }                
        agentRequest.close();
        return totalReaded;
    }
    public Response execute(Request request){
        request.secid=this.secid;
        HttpConn httpconn = new HttpConn();
        try{            
            httpconn.setup(address,port); 
            //TODO from request method
            httpconn.setMethod(request.method,"/");
            httpconn.setEntity(request.getRequestJson());
            httpconn.connect();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        }
        Response response=ResponseFactory.getResponseByActionType(httpconn.getHttpResponse(), request.getActionType());
        //Response response=new Response(httpconn.getHttpResponse());
        httpconn.close();
        return response;
    }
    public Response getMetaDataByName(String name){
        MetadataRequest request=new MetadataRequest();        
        request.setName(name);
        MetadataResponse response=(MetadataResponse)execute(request);
        if (!response.getStatus()){
            return response;
        }
        return response;
    }
    public Response moveFileLevelByName(String name,String host,StorageLevel lvlfrom,StorageLevel lvlto){
        MoveLevelRequest request=new MoveLevelRequest();        
        request.setName(name);
        request.setHost(host);
        request.setLvlFrom(lvlfrom);
        request.setLvlTo(lvlto);        
        return execute(request);
    }
    public Response moveFileByName(String name,String fromhost,String tohost,StorageLevel lvlfrom,StorageLevel lvlto){
        MoveFileRequest request=new MoveFileRequest();        
        request.setName(name);
        request.setFromHost(fromhost);
        request.setToHost(tohost);
        request.setLvlFrom(lvlfrom);
        request.setLvlTo(lvlto);        
        return execute(request);
    }
}
