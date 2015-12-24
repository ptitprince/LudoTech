package gui.utils.exceptions;

@SuppressWarnings("serial")
public class NotValidNumberFieldException extends Exception {

	private String fieldName;
	private String fieldValue;
	private String fieldType;

	public NotValidNumberFieldException(String fieldName, String fieldValue, String fieldType) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.fieldType = fieldType;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public String getFieldType() {
		return fieldType;
	}

}
