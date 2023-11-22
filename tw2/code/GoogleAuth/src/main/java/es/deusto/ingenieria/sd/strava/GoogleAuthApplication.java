package es.deusto.ingenieria.sd.strava;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class GoogleAuthApplication {

    private final Map<String, String> userDataMap = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(GoogleAuthApplication.class, args);
    }

    @PostConstruct
    public void init() {
        try {
            File file = ResourceUtils.getFile("classpath:static/users.txt");
            String content = Files.readString(Paths.get(file.getAbsolutePath()));

            content.lines().forEach(l -> {
                String[] userData = l.split(" ");
                if (userData.length == 2) {
                    userDataMap.put(userData[0], userData[1]);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public Map<String, String> getUserDataMap() {
        return userDataMap;
    }

}
