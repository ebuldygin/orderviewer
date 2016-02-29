
package ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CurrentTime_QNAME = new QName("http://endpoint.ws/", "currentTime");
    private final static QName _CurrentTimeResponse_QNAME = new QName("http://endpoint.ws/", "currentTimeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CurrentTime }
     * 
     */
    public CurrentTime createCurrentTime() {
        return new CurrentTime();
    }

    /**
     * Create an instance of {@link CurrentTimeResponse }
     * 
     */
    public CurrentTimeResponse createCurrentTimeResponse() {
        return new CurrentTimeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrentTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.ws/", name = "currentTime")
    public JAXBElement<CurrentTime> createCurrentTime(CurrentTime value) {
        return new JAXBElement<CurrentTime>(_CurrentTime_QNAME, CurrentTime.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrentTimeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://endpoint.ws/", name = "currentTimeResponse")
    public JAXBElement<CurrentTimeResponse> createCurrentTimeResponse(CurrentTimeResponse value) {
        return new JAXBElement<CurrentTimeResponse>(_CurrentTimeResponse_QNAME, CurrentTimeResponse.class, null, value);
    }

}
