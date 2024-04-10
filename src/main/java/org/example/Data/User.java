package org.example.Data;

import com.sun.istack.internal.NotNull;
import org.example.Data.Lot;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @OneToOne(mappedBy = "user")
    private Lot lot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id = " + id +
                " name = " + name +
                " lot = " + lot;
    }
}
