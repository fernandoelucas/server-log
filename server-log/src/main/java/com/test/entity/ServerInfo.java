package com.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "SERVER_INFO")
public class ServerInfo {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String idServer;
	private long duration;
	private String type;
	private String host;
	@Column(name = "ALERT", nullable = false)
	private boolean alert;
	
	public ServerInfo() {
	}

	public ServerInfo(String idServer, long duration, String type, String host, boolean alert) {
		super();
		this.idServer = idServer;
		this.duration = duration;
		this.type = type;
		this.host = host;
		this.alert = alert;
	}

	public long getId() {
		return id;
	}	

	public String getIdServer() {
		return idServer;
	}

	public void setIdServer(String idServer) {
		this.idServer = idServer;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}