package ws.endpoint;

import java.util.Date;
import javax.jws.WebService;

@WebService(endpointInterface = "ws.endpoint.TimeService",
        serviceName = "TimeService")
public class TimeServiceImpl implements TimeService {

    @Override
    public Date currentTime() {
        return new Date();
    }

}
