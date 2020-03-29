/**
 * 
 */
package demo.restful.service.data;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

/**
 * @author user
 *
 */
public class In28User extends RepresentationModel<In28User>{
	
	private Integer id;
	@Size(min=2,message="name should have atleast 2 Characters")
	private String name;
	@Past
	private Date birthDate;

	protected In28User() {
		
	}
	public In28User(Integer id, String name, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "In28User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
	
	

}
