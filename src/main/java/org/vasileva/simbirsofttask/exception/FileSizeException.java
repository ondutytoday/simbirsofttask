package org.vasileva.simbirsofttask.exception;
/*
* Исключение, возникающее, если файл боле чем заданный нами в properties
* */
public class FileSizeException extends Exception{
    public FileSizeException() {
    }

    //конструкторы
    public FileSizeException(String message) {
        super(message);
    }

    public FileSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSizeException(Throwable cause) {
        super(cause);
    }

    public FileSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
