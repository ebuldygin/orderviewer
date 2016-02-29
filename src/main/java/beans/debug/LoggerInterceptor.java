package beans.debug;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Logged
public class LoggerInterceptor implements Serializable {

    @AroundInvoke
    public Object log(InvocationContext invocationContext) throws Exception {
        Object result = null;
        try {
            Logger.getLogger(LoggerInterceptor.class.getName()).log(Level.FINER,
                    "{0} start", invocationContext.getMethod());
            for (Object parameter : invocationContext.getParameters()) {
                Logger.getLogger(LoggerInterceptor.class.getName()).log(Level.FINER,
                        "    - {0}", String.valueOf(parameter));
            }
            result = invocationContext.proceed();
            Logger.getLogger(LoggerInterceptor.class.getName()).log(Level.FINER,
                    "{0} end", invocationContext.getMethod());
        } catch (Exception e) {
            Logger.getLogger(LoggerInterceptor.class.getName()).log(Level.SEVERE,
                    invocationContext.getMethod() + " throws exception", e);
            throw e;
        }

        return result;
    }

}
