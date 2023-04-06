package com.fees.entity;


import com.fees.entity.helper.IdBasedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "fee_entry")
public class FeeEntry extends IdBasedEntity {
    @Column(length = 128, nullable = false, unique = false)
    private String s_name;
    @Column(length = 128, nullable = false, unique = false)
    private String s_class;
    @Column(length = 128, nullable = false, unique = false)
    private String s_fathername;
    @Column(length = 128, nullable = false, unique = false)
    private String s_fathermobile;
    @Column(length = 128, nullable = false, unique = false)
    private String s_fee;
    @Column(length = 128, nullable = false, unique = false)
    private Date createdAt;


    public FeeEntry() {
    }
    public String getS_name() {
        return s_name;
    }
    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_class() {
        return s_class;
    }

    public void setS_class(String s_class) {
        this.s_class = s_class;
    }

    public String getS_fathername() {
        return s_fathername;
    }

    public void setS_fathername(String s_fathername) {
        this.s_fathername = s_fathername;
    }

    public String getS_fathermobile() {
        return s_fathermobile;
    }

    public void setS_fathermobile(String s_fathermobile) {
        this.s_fathermobile = s_fathermobile;
    }

    public String getS_fee() {
        return s_fee;
    }

    public void setS_fee(String s_fee) {
        this.s_fee = s_fee;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
