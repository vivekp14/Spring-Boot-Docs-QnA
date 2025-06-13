package com.App.Spring.Boot.Docs.QnA.utils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;

@Component
public class DocumentExtractor {
    public String extractContent(MultipartFile file) throws Exception {
        BodyContentHandler handler = new BodyContentHandler(10 * 1024 * 1024); // 10MB limit
        Metadata metadata = new Metadata();
        try (InputStream inputStream = file.getInputStream()) {
            AutoDetectParser parser = new AutoDetectParser();
            parser.parse(inputStream, handler, metadata);
            return handler.toString().trim();
        }
    }
}

