package itmo.escience.dstorage.utils.responses;



import itmo.escience.dstorage.utils.enums.StorageActionType;
import org.apache.http.HttpResponse;

/**
 *
 * @author anton
 * 
 * Возвращает класс реализующий интерфейс Response в соответствии с типом действия при обращению к ядру
 */
public class ResponseFactory {
    public static Response getResponseByActionType(HttpResponse response,StorageActionType type){
        switch(type){
                case GET:return new DownloadResponse(response);
                case PUT: return new UploadResponse(response);
                case AGENTPUT: return new UploadResponse(response);
                case GET_METADATA: return new MetadataResponse(response);
                case MVLVL: return new Response(response);
                case CPLVL: return new Response(response);    
                case MVFILE: return new Response(response);
                case LIST:
                default :throw new RuntimeException("Unknown storage action type ");
        }
    }
}
