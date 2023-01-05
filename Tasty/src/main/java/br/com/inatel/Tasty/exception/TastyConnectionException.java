package br.com.inatel.Tasty.exception;

public class TastyConnectionException extends RuntimeException{
    public TastyConnectionException(String tastyBaseUrl){
        super(String.format("Was not possible to communicate with Tasty at location [%s]", tastyBaseUrl));
    }
}
