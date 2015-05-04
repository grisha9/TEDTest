package rzn.ru.myasoedov.tedtest.dto;

/**
 * Created by grisha on 03.05.15.
 */
public class ResponseWrapper<T> {
    private Exception exception;
    private T response;

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
