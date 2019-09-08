package com.test.entity;

import java.util.Date;

public class ServerLog implements Comparable<ServerLog> {

	private String id;
	private State state;
	private String type;
	private String host;
	private Date timestamp;
	
	public String getId() {
              return id;
	}
	
	public void setId(String id) {
              this.id = id;
	}
	
	public State getState() {
              return state;
	}

	public void setState(State state) {
		this.state = state;
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

   public Date getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(Date timestamp) {
      this.timestamp = timestamp;
   }      

	@Override
	public int compareTo(ServerLog serverLog) {
		for (int i = 0; i < this.getId().length() && i < serverLog.getId().length(); i++) {
			if ((int) this.getId().charAt(i) == (int)serverLog.getId().charAt(i)) {
				continue;
			} 
			else {
				return (int) this.getId().charAt(i) - (int)serverLog.getId().charAt(i);
			}
		}

	    // Edge case for strings like
	    // String 1="Geeky" and String 2="Geekyguy"
	    if ( this.getId().length() < serverLog.getId().length()) {
	        return ( this.getId().length()-serverLog.getId().length());
	    } 
	
	    else if ( this.getId().length() > serverLog.getId().length()) {
	        return ( this.getId().length()-serverLog.getId().length());
	    }
	
	    // If none of the above conditions is true,
	    // it implies both the strings are equal
	    else {
	        return 0;
	
	    }
	}

}