/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otago.lecturercommon.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Admin-pc
 */
@Table(name = "programmapping")
@Entity
public class ProgramMapping {

    @Id
    @GeneratedValue
    @Column(name = "id", length = 11)
    private int id;

    @Column(name = "userid", columnDefinition = "BIGINT(20)")
    private int userId;

    /*
     * @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
     * 
     * @JoinColumn(name = "tagid") private Tag tag;
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdon")
    private Date createdOn;

    public ProgramMapping() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
