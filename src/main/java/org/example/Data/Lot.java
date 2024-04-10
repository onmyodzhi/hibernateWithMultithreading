package org.example.Data;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "Lots")
public class Lot {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @NotNull
    private String title;
    @Column(name = "current_bet")
    @NotNull
    private Integer current_bet;

    @OneToOne
    @JoinColumn(name = "current_owner")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCurrentBet() {
        return current_bet;
    }

    public void setCurrentBet(Integer current_bet) {
        this.current_bet = current_bet;
    }

    public void setCurrentOwner(User user) {
        this.user = user;
    }

    public User getCurrentOwner() {
        return user;
    }

    @Override
    public String toString() {
        return "id = " + id + " title = " + title + " current_bet = " + current_bet;
    }
}
