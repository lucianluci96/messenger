package org.myproject.messenger.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {

	private String errorMessage;
	private int errorCod;
	private String documentation;

	public ErrorMessage() {
		
		
	}

	public ErrorMessage(String errorMessage, int errorCod, String documentation) {
		super();
		this.errorMessage = errorMessage;
		this.errorCod = errorCod;
		this.documentation = documentation;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCod() {
		return errorCod;
	}

	public void setErrorCod(int errorCod) {
		this.errorCod = errorCod;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	
	

}
