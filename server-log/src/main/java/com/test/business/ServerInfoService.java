package com.test.business;

import java.util.List;

import com.test.entity.ServerInfo;


public interface ServerInfoService {	
	
	public ServerInfo addServerInfo(ServerInfo serverInfo);
	
	public void LoadFile(String filePath);
	
	public List<ServerInfo> getAllServerInfo();

}
