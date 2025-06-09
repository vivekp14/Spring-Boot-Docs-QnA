package com.App.Spring.Boot.Docs.QnA.controller;
import com.App.Spring.Boot.Docs.QnA.dto.QnaRequest;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/qna")
public class QnaController {
    @Autowired
    private QnaService qnaService;

    @PostMapping
    public ResponseEntity<List<Document>> search(@RequestBody QnaRequest request) {
        return ResponseEntity.ok(qnaService.search(request));
    }
}
