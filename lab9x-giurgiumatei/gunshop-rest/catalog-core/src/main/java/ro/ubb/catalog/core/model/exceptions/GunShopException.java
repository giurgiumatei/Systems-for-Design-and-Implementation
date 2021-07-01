package ro.ubb.catalog.core.model.exceptions;

public class GunShopException extends RuntimeException{

    public GunShopException (String message)
    {
        super(message);
    }

    public GunShopException (String message, Throwable cause) {
        super(message, cause);
    }

    public GunShopException(Throwable cause)
    {
        super(cause);
    }
}
