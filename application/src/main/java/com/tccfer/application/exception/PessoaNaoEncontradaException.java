package com.tccfer.application.exception;

public class PessoaNaoEncontradaException extends RuntimeException{
    public PessoaNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
