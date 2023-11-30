package informatica.support.estagio.desafio.infrastructure;

import informatica.support.estagio.desafio.infrastructure.external.FeedDbService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class MonitorDb {
    private FeedDbService feedDbService;
    public MonitorDb(FeedDbService feedDbService) {
        this.feedDbService = feedDbService;
    }

    @PostConstruct
    public void init() throws URISyntaxException, IOException, InterruptedException {
        if (this.feedDbService.productTableEmpty()) {
            this.feedDbService.feedDb();
        }
    }
}
