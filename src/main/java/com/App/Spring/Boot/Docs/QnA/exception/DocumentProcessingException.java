package com.App.Spring.Boot.Docs.QnA.exception;

/**
 * Custom exception for document processing errors.
 */
public class DocumentProcessingException extends RuntimeException {
    public DocumentProcessingException(String message) {
        super(message);
    }

    public DocumentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
