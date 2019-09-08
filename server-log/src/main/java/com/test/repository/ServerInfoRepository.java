package com.test.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.entity.ServerInfo;

@Repository
public interface ServerInfoRepository extends CrudRepository<ServerInfo, Long> {
	List<ServerInfo> findAll();
	ServerInfo save(ServerInfo serverInfo);
}