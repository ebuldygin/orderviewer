package beans.services;

import beans.view.OrderItemEditBean;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import javax.xml.namespace.QName;
import ws.client.TimeService;
import ws.client.TimeService_Service;

public class TimeServiceBean implements Serializable {

    private final static String CONFIG_FILENAME = "config.properties";
    private final static URL TIMESERVICE_WSDL_LOCATION;
    private final static QName TIMESERVICE_QNAME;

    static {
        URL url = null;
        QName qname = null;
        try {
            Properties prop = new Properties();
            InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(CONFIG_FILENAME);
            if (is != null) {
                prop.load(is);
                url = new URL(prop.getProperty("timeservice.wsdl.location"));
                qname = new QName(prop.getProperty("timeservice.qname.namespaceURI"),
                        prop.getProperty("timeservice.qname.localPart"));
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TimeServiceBean.class.getName()).log(Level.SEVERE,
                    "Error while applying TimeService properties", ex);
        }
        TIMESERVICE_WSDL_LOCATION = url;
        TIMESERVICE_QNAME = qname;
    }

    public Date requestDate() {
        TimeService_Service timeService;
        if (TIMESERVICE_WSDL_LOCATION != null && TIMESERVICE_QNAME != null) {
            timeService = new TimeService_Service(TIMESERVICE_WSDL_LOCATION, TIMESERVICE_QNAME);
        } else {
            timeService = new TimeService_Service();
        }
        TimeService ts = timeService.getTimeServiceImplPort();
        return ts.currentTime().toGregorianCalendar().getTime();
    }

}
