package prestrezniki;


import anotacije.BeleziKlice;
import zrna.BelezenjeKlicevZrno;
import zrna.NajemZrno;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {
    @Inject
    private BelezenjeKlicevZrno zrno;

    private static Logger log = Logger.getLogger(NajemZrno.class.getName());

    @AroundInvoke
    public Object logKlic(InvocationContext context)throws Exception{

        //entering method
        zrno.beleziKlic();
        Object x = context.proceed();
        //leaving method
        log.info("Leaving method");
        return x;
    }
}
