package magniflyer.user;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

class Account implements Serializable {

    private String firstName;

    private String middleInitial;

    private String lastName;

    private String email;

    private String password;

    private final int CUSTOMER_ID;

    private final LocalDate DATE_CREATED;

    public Account(String firstName, String middleInitial, String lastName, String email, String password, int customerId) {
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.CUSTOMER_ID = customerId;
        this.DATE_CREATED = LocalDate.now();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCustomerID() {
        return CUSTOMER_ID;
    }

    public LocalDate getDateCreated() {
        return DATE_CREATED;
    }

    @Override
    public String toString() {
        return firstName + " " + (middleInitial.equals("") ? "" : middleInitial + ". ")  + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(CUSTOMER_ID, account.getCustomerID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(CUSTOMER_ID);
    }

}
