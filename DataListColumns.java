package FlexToWebSquare;

public class DataListColumns {
	private String id;
	private String name;
	private String dataType;
	
	public DataListColumns(String id, String name, String dataType){
		this.id = id;
		this.name = name;
		this.dataType = dataType;
	}
	
	public DataListColumns() {
		// TODO Auto-generated constructor stub
		id = "";
		name = "";
		dataType = "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
}
