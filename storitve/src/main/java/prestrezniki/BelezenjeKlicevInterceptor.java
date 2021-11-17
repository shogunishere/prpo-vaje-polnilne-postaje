package prestrezniki;


import anotacije.BeleziKlice;
import zrna.BelezenjeKlicevZrno;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;


@BeleziKlice
@Interceptor
public class BelezenjeKlicevInterceptor {
    @Inject
    private BelezenjeKlicevZrno zrno;

    private static Logger log = Logger.getLogger(BelezenjeKlicevInterceptor.class.getName());

    @AroundInvoke
    public Object log(InvocationContext context)throws Exception{
        //entering method
        zrno.beleziKlic();
        Object x = context.proceed();
        //leaving method
        log.info("Leaving method");
        return x;
    }
}
