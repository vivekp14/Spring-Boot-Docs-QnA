package com.App.Spring.Boot.Docs.QnA.controller;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.dto.QnaRequestDTO;
import com.App.Spring.Boot.Docs.QnA.service.QnaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/qna")
public class QnaController {
    private final QnaService qnaService;

    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @PostMapping
    public ResponseEntity<Page<DocumentDTO>> answerQuestion(
            @Valid @RequestBody QnaRequestDTO request,
            Pageable pageable) {
        return ResponseEntity.ok(qnaService.answerQuestion(request.getQuestion(), pageable));
    }
}
