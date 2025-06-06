package com.App.Spring.Boot.Docs.QnA.dto;

import lombok.Data;
import java.util.List;

@Data
public class QAResponse {
    private List<String> matchingSnippets;
}
