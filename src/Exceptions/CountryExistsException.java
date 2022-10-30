package Exceptions;

public class CountryExistsException extends Exception{
    public CountryExistsException(){super("This id already exits");}
}
