package synergy.botikspring.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;

    public ContactDto(Long id, String firstName, String lastName, String middleName, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phone = phone;
    }

    public ContactDto() {

    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName + " " + phone;
    }
}
