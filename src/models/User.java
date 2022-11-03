package models;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class User extends Entity<Long>{
    private String lastname;
    private String surname;
    private LocalDate birthDate;

    public User(String lastname, String surname, LocalDate birthDate) {
        this.lastname = lastname;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getYears(){
        return Math.abs(Period.between(birthDate, LocalDate.now()).getYears());
    }

    @Override
    public String toString() {
        return "The user " + this.getId() + " " + this.getLastname() + " " + this.getSurname() + " " + this.getYears() + " years old";
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getId());
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        User student = (User) o;
        return Objects.equals(student.getId(), this.getId());
    }
}
