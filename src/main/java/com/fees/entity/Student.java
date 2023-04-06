package com.fees.entity;


import com.fees.entity.helper.IdBasedEntity;
import javax.persistence.*;

@Entity
@Table(name = "student")
public class Student extends IdBasedEntity {
    @Column(length = 128, nullable = false, unique = false)
    private String name;
    @Column(length = 128, nullable = false, unique = false)
    private String code;

    public Student() {
    }

    public Student(String name, String code) {
        this.name = name;
        this.code = code;
    }


}
