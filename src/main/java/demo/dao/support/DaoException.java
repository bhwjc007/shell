package shell.framework.dao.support;

/**
 * dao层异常
 * @author yangchangming
 *
 */
@SuppressWarnings("serial")
public class DaoException extends RuntimeException {

	public DaoException(String message) {
		super(message);
	}
	
	public DaoException(Throwable throwable){
		super(throwable);
	}
	
	public DaoException(String message,Throwable throwable){
		super(message, throwable);
	}
	
	
}
