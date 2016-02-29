package beans.debug;

import java.io.Serializable;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.jboss.logging.Logger;

@Interceptor
@Logged
public class LoggerInterceptor implements Serializable {

    @AroundInvoke
    public Object log(InvocationContext invocationContext) throws Exception {
        Object result = null;
        try {
            Logger.getLogger(getClass().getName()).log(Logger.Level.DEBUG, invocationContext.getMethod() + " start");
            for (Object parameter : invocationContext.getParameters()) {
                Logger.getLogger(getClass().getName()).log(Logger.Level.DEBUG, "    - " + String.valueOf(parameter));
            }
            result = invocationContext.proceed();
            Logger.getLogger(getClass().getName()).log(Logger.Level.DEBUG, invocationContext.getMethod() + " end");
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Logger.Level.ERROR,
                    invocationContext.getMethod() + " throws exception", e);
            throw e;
        }
        return result;
    }

}
