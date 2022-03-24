package io.springbatch.springbatchlecture.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "tb_address")
@ToString(of = {"id", "location"})
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id;
    private String location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer2 customer2;
}
