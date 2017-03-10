package br.usp.iq.lbi.caravela.domain.exception;

public class DomainException extends RuntimeException {
	
	private static final long serialVersionUID = 1725684218804018118L;

	public DomainException(String message) {
		super(message);
	}
	
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

}
