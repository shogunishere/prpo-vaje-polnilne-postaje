package prestrezniki;


import anotacije.BeleziKlice;
import zrna.BelezenjeKlicevZrno;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {
    @Inject
    private BelezenjeKlicevZrno zrno;

    @AroundInvoke
    public Object log(InvocationContext context)throws Exception{
        zrno.beleziKlic();
        return context.proceed();
    }
}
