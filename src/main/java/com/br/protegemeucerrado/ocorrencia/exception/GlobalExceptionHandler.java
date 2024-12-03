package com.br.protegemeucerrado.ocorrencia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Tratando a exceção OcorrenciaException
    @ExceptionHandler(OcorrenciaException.class)
    public ResponseEntity<String> handleOcorrenciaException(OcorrenciaException ex) {
        // Retorna a mensagem da exceção com status BAD_REQUEST
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Tratando qualquer outra exceção genérica
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("Erro interno do servidor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
