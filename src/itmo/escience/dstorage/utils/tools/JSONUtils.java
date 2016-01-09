package itmo.escience.dstorage.utils.tools;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author anton
 */
public class JSONUtils {
    public static JSONObject parseJSON(String obj) {
             
         JSONParser jsonParser = new JSONParser();
         JSONObject jsonObject = new JSONObject();
         try {
             jsonObject = (JSONObject)jsonParser.parse(obj);
         } 
         catch (ParseException ex) {
            System.out.println("JSON Parse Error. Object:"+ex.getUnexpectedObject()+"; Position:"+ex.getPosition());
            return jsonObject; 
         }
         catch (Exception ex) {                  
            System.out.println("JSON Exception:"+ex.getMessage());
            return jsonObject; 
         } 
         
         return jsonObject;
    }
    
}
