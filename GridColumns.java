package FlexToWebSquare;

public class GridColumns {
	private String dataField;
	private String properties;
	private String textAlign;
	
	public GridColumns(String dataField, String properties, String textAlign){
		this.dataField = dataField;
		this.properties = properties;
		this.textAlign = textAlign;
	}

	public GridColumns() {
		// TODO Auto-generated constructor stub
		dataField = "";
		properties = "";
		textAlign = "";
	}

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getTextAlign() {
		return textAlign;
	}

	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}
}
