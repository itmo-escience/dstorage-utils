package itmo.escience.dstorage.utils.network;

import itmo.escience.dstorage.utils.enums.StorageMethod;
import itmo.escience.dstorage.utils.tools.JSONUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpConn {
    private HttpParams params;
    private HttpRequestExecutor httpexecutor;
    private BasicHttpEntityEnclosingRequest httpconnent; 
    private BasicHttpRequest httpconn;    
    private DefaultHttpClientConnection conn;
    private HttpContext context;
    private HttpProcessor httpproc;
    private HttpEntity responseEntity;
    private String host;
    private HttpResponse response;
    private int port;
    private boolean isEntity=false;
                
    public void setup(String httpHost, String httpPort) throws Exception{
        params = new SyncBasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "UTF-8");
        HttpProtocolParams.setUserAgent(params, "DSagent");
        HttpProtocolParams.setUseExpectContinue(params, true);        
        httpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
                new RequestContent(),
                new RequestTargetHost(),
                new RequestConnControl(),
                new RequestUserAgent(),
                new RequestExpectContinue()});
        httpexecutor = new HttpRequestExecutor();
        context = new BasicHttpContext(null);
        //HttpHost host = new HttpHost(Agent.getConfig().getProperty("StorageCoreAddress"),Integer.valueOf(Agent.getConfig().getProperty("StorageCorePort"))); 
        HttpHost host = new HttpHost(httpHost,Integer.parseInt(httpPort)); 
        conn = new DefaultHttpClientConnection();
        context.setAttribute(ExecutionContext.HTTP_CONNECTION, conn);
        context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, host);
        Socket socket = new Socket(host.getHostName(), host.getPort());
        conn.bind(socket, params);
    }
    public void setEntity( JSONObject json) throws UnsupportedEncodingException{
        StringEntity strEntRegister = new StringEntity (json.toString());
        //Client.log.info("JSON to StorageCore:"+json.toString());
        //Agent.log.info("set entity json:"+json.toString());
        strEntRegister.setContentType("application/json");   
        httpconnent.setEntity(strEntRegister);                
    }
    public void setEntity( InputStreamEntity entity) throws UnsupportedEncodingException{                           
        entity.setContentType("binary/octet-stream");
        entity.setChunked(true);    
        httpconnent.setEntity(entity);
    }
    
public void setMethod(StorageMethod method, String requestline){
        if (method.equals(StorageMethod.PUT)){
            //httpconnent =new HttpPost(requestline);            
            httpconnent=new BasicHttpEntityEnclosingRequest ("POST",requestline);
            httpconnent.setParams(params);
            isEntity=true;
        }
        if (method.equals(StorageMethod.DELETE)){
            httpconn=new BasicHttpRequest(method.toString(),requestline);
            //httpconn=new HttpDelete(requestline); 
            httpconn.setParams(params);
        }
        if (method.equals(StorageMethod.GET)){
            
            httpconn=new BasicHttpRequest(method.toString(),requestline); 
            //httpconn=new HttpGet(requestline); 
            httpconn.setParams(params);
        }
    }
    public void setHeader(String nameHeader, String headerContent){
        if(httpconnent!=null)httpconnent.setHeader(nameHeader,headerContent);
        else httpconn.setHeader(nameHeader,headerContent);
    }
    public void connect() {
        try {
            final BasicHttpRequest http;
            if (isEntity) http=httpconnent;
            else http= httpconn;
            httpexecutor.preProcess(http, httpproc, context);
            //TODO coundn't get response
            response = httpexecutor.execute(http, conn, context);
            response.setParams(params);
            httpexecutor.postProcess(response, httpproc, context);  
            responseEntity= response.getEntity();
        } catch (HttpException ex) {
            Logger.getLogger(HttpConn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public HttpResponse getHttpResponse(){return response;}
    public JSONObject getResponse(){
        String entityContent=null;
        try {
            entityContent = EntityUtils.toString(responseEntity);
        } catch (IOException ex) {
            Logger.getLogger(HttpConn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (org.apache.http.ParseException ex) {
            Logger.getLogger(HttpConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return JSONUtils.parseJSON(entityContent);
    }
    public String getStringResponse()throws IOException{
        String entityContent = EntityUtils.toString(responseEntity);
        return entityContent;
    }
    public InputStream getStreamResponse() throws IOException{
        return responseEntity.getContent();       
    }
    public void close(){
        try {
            conn.close();
        } catch (IOException ex) {
            Logger.getLogger(HttpConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }         
}
