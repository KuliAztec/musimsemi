package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "player_stats")
public class Player {
    @Id
    private String name;

    private String nation;

    @Column(name = "pos")  // This maps to the database column "pos"
    private String position;  // This is the Java field name

    private Integer age;
    private String team;
    private int mp;
    private int starts;
    private int min;
    private int gls;
    private int ast;
    private int pk;
    private int crdy;
    private int crdr;
    private double xg;
    private double xag;

    // Default constructor required by JPA
    public Player() {}

    public Player(String Name, String Nation, String Position, int Age, int Match_Played, int Starts, int Minutes, int Goals, int Assist, int Penalties, int CardYellow, int CardRed, double xG, double xAG, String Team) {
        this.name = Name;
        this.nation = Nation;
        this.position = Position;
        this.age = Age;
        this.mp = Match_Played;
        this.starts = Starts;
        this.min = Minutes;
        this.gls = Goals;
        this.ast = Assist;
        this.pk = Penalties;
        this.crdy = CardYellow;
        this.crdr = CardRed;
        this.xg = xG;
        this.xag = xAG;
        this.team = Team;
    }

    public String getName() {
        return name;
    }
    
    public String getNation() {
        return nation;
    }

    public String getPosition() {
        return position;
    }

    public Integer getAge() {
        return age;
    }

    public int getMatch_Played() {
        return mp;
    }

    public int getStarts() {
        return starts;
    }

    public int getMinutes() {
        return min;
    }

    public int getGoals() {
        return gls;
    }

    public int getAssist() {
        return ast;
    }

    public int getPenalties() {
        return pk;
    }

    public int getCardYellow() {
        return crdy;
    }

    public int getCardRed() {
        return crdr;
    }

    public double getxG() {
        return xg;
    }

    public double getxAG() {
        return xag;
    }

    public String getTeam() {
        return team;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public void setNation(String Nation) {
        this.nation = Nation;
    }

    public void setPosition(String Position) {
        this.position = Position;
    }

    public void setAge(Integer Age) {
        this.age = Age;
    }

    public void setMatch_Played(int Match_Played) {
        this.mp = Match_Played;
    }

    public void setStarts(int Starts) {
        this.starts = Starts;
    }

    public void setMinutes(int Minutes) {
        this.min = Minutes;
    }

    public void setGoals(int Goals) {
        this.gls = Goals;
    }

    public void setAssist(int Assist) {
        this.ast = Assist;
    }

    public void setPenalties(int Penalties) {
        this.pk = Penalties;
    }

    public void setCardYellow(int CardYellow) {
        this.crdy = CardYellow;
    }

    public void setCardRed(int CardRed) {
        this.crdr = CardRed;
    }

    public void setxG(double xG) {
        this.xg = xG;
    }

    public void setxAG(double xAG) {
        this.xag = xAG;
    }

    public void setTeam(String Team) {
        this.team = Team;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", nation=" + nation +
                ", position='" + position + '\'' +
                ", age=" + age +
                ", mp=" + mp +
                ", starts=" + starts +
                ", min=" + min +
                ", gls=" + gls +
                ", ast=" + ast +
                ", pk=" + pk +
                ", crdy=" + crdy +
                ", crdr=" + crdr +
                ", xg=" + xg +
                ", xag=" + xag +
                ", team='" + team + '\'' +
                '}';
    }
}
