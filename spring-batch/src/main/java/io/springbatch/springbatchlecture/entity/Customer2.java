package io.springbatch.springbatchlecture.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "tb_customer")
@ToString(of = {"id", "username", "age"})
public class Customer2 {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    private String username;

    private int age;

    @OneToOne(mappedBy = "customer2", fetch = FetchType.LAZY)
    private Address address;
}
