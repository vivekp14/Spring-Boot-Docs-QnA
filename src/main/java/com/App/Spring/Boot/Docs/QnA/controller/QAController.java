package com.App.Spring.Boot.Docs.QnA.controller;

import com.App.Spring.Boot.Docs.QnA.dto.QARequest;
import com.App.Spring.Boot.Docs.QnA.dto.QAResponse;
import com.App.Spring.Boot.Docs.QnA.service.QAService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qa")
@RequiredArgsConstructor
public class QAController {
    private final QAService qaService;
    private static final Logger logger = LoggerFactory.getLogger(QAController.class);

    @PostMapping("/query")
    public ResponseEntity<QAResponse> query(@RequestBody QARequest request) {
        logger.info("Received QA query: {}", request.getQuestion());
        QAResponse response = qaService.answerQuestion(request.getQuestion());
        return ResponseEntity.ok(response);
    }
}
