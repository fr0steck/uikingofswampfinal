package com.example.uikingofswampfinal.data.entity;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class SamplePlayer extends AbstractEntity {
	
	private String nickname;
	private String occupation;
	private BigDecimal goldamount;
	private BigDecimal troopsamount;
	private BigDecimal movementspeed;
	private Boolean active;
	private BigDecimal attackpoints;
	private BigDecimal defensepoints;
	private BigDecimal health;
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getOccupation() {
		return occupation;
	}
	
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public BigDecimal getGoldamount() {
		return goldamount;
	}
	
	public void setGoldamount(BigDecimal goldamount) {
		this.goldamount = goldamount;
	}
	
	public BigDecimal getTroopsamount() {
		return troopsamount;
	}
	
	public void setTroopsamount(BigDecimal troopsamount) {
		this.troopsamount = troopsamount;
	}
	
	public BigDecimal getMovementspeed() {
		return movementspeed;
	}
	
	public void setMovementspeed(BigDecimal movementspeed) {
		this.movementspeed = movementspeed;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public BigDecimal getAttackpoints() {
		return attackpoints;
	}
	
	public void setAttackpoints(BigDecimal attackpoints) {
		this.attackpoints = attackpoints;
	}
	
	public BigDecimal getDefensepoints() {
		return defensepoints;
	}
	
	public void setDefensepoints(BigDecimal defensepoints) {
		this.defensepoints = defensepoints;
	}
	
	public BigDecimal getHealth() {
		return health;
	}
	
	public void setHealth(BigDecimal health) {
		this.health = health;
	}
}