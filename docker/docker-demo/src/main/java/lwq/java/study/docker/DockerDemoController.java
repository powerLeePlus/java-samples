package lwq.java.study.docker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lwq
 * @create 2019-08-29 上午 11:59
 */
@RestController
public class DockerDemoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DockerDemoController.class);
    @GetMapping("/")
    public String index() {
        LOGGER.info("Hello Docker!");
        return "Hello Docker!";
    }
}
