package model;

public class Manager {

	private Integer id;
	private String names;
	private String last_names;
	private String document_type;
	private String email;
	private String document_number;
	private String cell_phone;
	private String status;

	public Manager() {
	}

	public Manager(Integer id, String names, String last_names, String document_type, String email, String document_number, String cell_phone, String status) {
		super();
		this.id = id;
		this.names = names;
		this.last_names = last_names;
		this.document_type = document_type;
		this.email = email;
		this.document_number = document_number;
		this.cell_phone = cell_phone;
		this.status = status;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}
	
	public String getLast_names() {
		return last_names;
	}

	public void setLast_names(String last_names) {
		this.last_names = last_names;
	}

	
	public String getDocument_type() {
		return document_type;
	}

	public void setDocument_type(String document_type) {
		this.document_type = document_type;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDocument_number() {
		return document_number;
	}

	public void setDocument_number(String document_number) {
		this.document_number = document_number;
	}
	
	public String getCell_phone() {
		return cell_phone;
	}

	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

	@Override
	public String toString() {
		String data = "[id: " + this.id;
		data += ", names: " + this.names;
		data += ", last_name: " + this.last_names;
		data += ", document_type: " + this.document_type;
		data += ", email: " + this.email;
		data += ", document_number: " + this.document_number;
		data += ", cell_phone: " + this.cell_phone;
		data += ", status: " + this.status + "]";
		return data;
	}

}
