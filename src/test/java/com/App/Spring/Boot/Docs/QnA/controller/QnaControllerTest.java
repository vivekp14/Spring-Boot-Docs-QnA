package com.App.Spring.Boot.Docs.QnA.controller;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.dto.QnaRequestDTO;
import com.App.Spring.Boot.Docs.QnA.service.QnaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QnaController.class)
class QnaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QnaService qnaService;

    @Autowired
    private ObjectMapper objectMapper;

    private QnaRequestDTO qnaRequest;

    @BeforeEach
    void setUp() {
        qnaRequest = new QnaRequestDTO();
        qnaRequest.setQuestion("What is the content?");
    }

    @Test
    void testAnswerQuestion() throws Exception {
        Page<DocumentDTO> page = new PageImpl<>(Collections.emptyList());
        when(qnaService.answerQuestion(anyString(), any())).thenReturn(page);
        mockMvc.perform(post("/api/v1/qna")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(qnaRequest)))
                .andExpect(status().isOk());
        verify(qnaService).answerQuestion(eq("What is the content?"), any());
    }
}

