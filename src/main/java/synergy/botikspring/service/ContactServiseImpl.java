package synergy.botikspring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import synergy.botikspring.Contacts;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiseImpl implements ContactServise {

    @Value("${classpath:contacts.csv}")
    private String csvFilePath;

    @Override
    public List<Contacts> findAll() {
        Resource resource = new ClassPathResource(csvFilePath);
        if (!resource.exists()) {
            throw new IllegalArgumentException("Файл не найден: " + csvFilePath);
        }

        List<Contacts> contact = new ArrayList<>();
        try {
            InputStream is = resource.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    contact.add(new Contacts(
                            parts[0], parts[1], parts[2], parts[3]
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return contact;
    }
}
