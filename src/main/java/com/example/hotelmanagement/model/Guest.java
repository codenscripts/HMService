package com.example.hotelmanagement.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=30)
    private String firstName;

    @Column(nullable=false, length=30)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    public Guest() {
    }


    public Guest(String firstName, String lastName, String email) {
       if (firstName == null || lastName == null) {
           throw new IllegalArgumentException("Null or empty fields are not allowed");
       }
        this.email = Objects.requireNonNull(email, "Null or empty fields are not allowed");
       this.firstName = firstName;
       this.lastName = lastName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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


    @Override
    public String toString() {
        return "Guest{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        Guest guest = (Guest) obj;
        return Objects.equals(id, guest.id);
    }
}
