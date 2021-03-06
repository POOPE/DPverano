
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = "title")
})
public class Topic extends DomainEntity {

	private String	title;
	private String	spanish;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	public String getSpanish() {
		return this.spanish;
	}
	public void setSpanish(final String spanish) {
		this.spanish = spanish;
	}

}
