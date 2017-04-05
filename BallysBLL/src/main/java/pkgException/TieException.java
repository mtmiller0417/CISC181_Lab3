package pkgException;

public class TieException extends Exception
{ 
    @Override
    public String getMessage() {
    	return "You have a TIE!";
    }
}
