package zrna;

import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

@ApplicationScoped
public class BelezenjeKlicevZrno {
    private static Logger log = Logger.getLogger(BelezenjeKlicevZrno.class.getName());
    private int stKlicev = 0;

    public void beleziKlic() {
        stKlicev++;
        log.info("Število vseh dosedanjih klicev: " + stKlicev);
    }
}
