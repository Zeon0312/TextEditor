package main;
import java.time.LocalDate;


public class Student {
	 
	 String id;
      String name;
      String address;
      LocalDate dateofBirth;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDate getDateofBirth() {
		return dateofBirth;
	}
	public void setDateofBirth(LocalDate dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
	public Student(String id, String name, String address, LocalDate dateofBirth) {
		if (id == null || name == null || address == null || dateofBirth == null) {
	         throw new IllegalArgumentException("Missing attribute");
	     }
		this.id = id;
		this.name = name;
		this.address = address;
		this.dateofBirth = dateofBirth;
	}
	 @Override
		public String toString() {
			return "Student [id=" + id + ", name=" + name + ", address=" + address + ", dateofBirth=" + dateofBirth + "]";
		}
      
}
