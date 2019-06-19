package com.hawk.demo.ztree.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2019-04-10.
 */
@Entity
@Table(name = "depart_ment")
public class DepartMent  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String name;

    @JsonIgnore
    @ManyToOne
    private DepartMent parent;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parent")
    private List<DepartMent> children=new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartMent getParent() {
        return parent;
    }

    public void setParent(DepartMent parent) {
        this.parent = parent;
    }

    public List<DepartMent> getChildren() {
        return children;
    }

    public void setChildren(List<DepartMent> children) {
        this.children = children;
    }
    @Transient
    public Integer getpId()
    {
        return parent==null?null:parent.getId();
    }
    @Transient
    public Boolean getIsParent()
    {
        return children.isEmpty()?false:true;
    }
}
