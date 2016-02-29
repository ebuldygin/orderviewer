package ws.endpoint;

import java.util.Date;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface TimeService {
    
    @WebMethod
    Date currentTime();
    
}
