package pkgException;

public class HandException extends Exception
{ 
    @Override
    public String getMessage() {
    	return "Your Hand has more than 5 cards!";
    }
}
