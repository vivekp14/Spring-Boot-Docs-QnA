package com.App.Spring.Boot.Docs.QnA.messaging;
import com.App.Spring.Boot.Docs.QnA.config.RabbitMQConfig;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.service.DocumentIngestionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DocumentMessageListener {
    private final DocumentIngestionService ingestionService;

    public DocumentMessageListener(DocumentIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @RabbitListener(queues = RabbitMQConfig.DOCUMENT_QUEUE)
    public void onMessage(DocumentDTO documentDTO) {
        ingestionService.processDocument(documentDTO);
    }
}

