package com.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.test.business.ServerInfoService;
import com.test.entity.ServerInfo;
import com.test.repository.ServerInfoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ServerInfoTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerInfoTest.class);
    
    @Autowired
    private ServerInfoService serverInfoService;
    
    @Autowired
    ServerInfoRepository serverInfoRepository;
    
    ServerInfo serverInfo;
    
    @Before
    public void init(){

    	
    }

    @Test
    public void testFindAll() {
    	LOGGER.info("STARTED: testFindAll()");
    	serverInfoRepository.save(new ServerInfo("abc", 6,"APP","thor",true));
    	serverInfoRepository.save(new ServerInfo("bac",3,"APP","thor",false));
        assertTrue(serverInfoRepository.findAll().size()>0);
        LOGGER.info("FINISH: testFindAll()");
    }
    
    @Test
    public void testSave() {
    	LOGGER.info("STARTED: testSave()");
    	  assertTrue(serverInfoRepository.save(new ServerInfo("def", 8,"APP","thor",true)).getId()>0);
    	  LOGGER.info("FINISH: testSave()");
    }
    
    @Test
    public void testLoadFile() {
    	LOGGER.info("STARTED: testLoadFile()");
    	
    	serverInfoService.LoadFile("src/main/resources/outputFile2.txt");
    	List<ServerInfo> serverInfoList=serverInfoService.getAllServerInfo();
    	serverInfoList.forEach(action->{
    		if (action.isAlert()==true)
    			assertEquals(action.isAlert(), action.getDuration()>4);
    		if (action.isAlert()==false)
    			assertEquals(!action.isAlert(), action.getDuration()<=4);
    	});
    	LOGGER.info("FINISH: testLoadFile()");
    }

}

