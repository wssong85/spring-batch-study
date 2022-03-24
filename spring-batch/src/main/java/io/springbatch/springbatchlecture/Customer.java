package io.springbatch.springbatchlecture;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity
@Data
public class Customer {

//    @Id
//    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String birthdate;
}
