package br.usp.iq.lbi.caravela.domain.exception;

public class ServiceAlreadyIsRunningException extends DomainException {

	private static final long serialVersionUID = 7101536026074271801L;

	public ServiceAlreadyIsRunningException(String message) {
		super(message);
	}
	
	public ServiceAlreadyIsRunningException(String message, Throwable cause) {
		super(message, cause);
	}


}
