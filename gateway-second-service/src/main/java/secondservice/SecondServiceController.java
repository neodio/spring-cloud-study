package secondservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/second-service")
@RequiredArgsConstructor
@Slf4j
public class SecondServiceController {

    private final Environment env;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Second service.";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        log.info(header);
        return "hello World in Second Service";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("server port={}", request.getServerPort());
        return String.format("hi, there. This is a message from Second Service on PORT %s", env.getProperty("local.server.port"));
    }
}
