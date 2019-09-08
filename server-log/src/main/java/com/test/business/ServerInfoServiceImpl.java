package com.test.business;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.entity.ServerInfo;
import com.test.entity.ServerLog;
import com.test.entity.State;
import com.test.repository.ServerInfoRepository;


@Service
public class ServerInfoServiceImpl implements ServerInfoService{	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerInfoServiceImpl.class);
	
	@Autowired
	ServerInfoRepository serverInfoRepository;	
	
    private static final Set<ServerLog> serverLogsStarted = new TreeSet<ServerLog>();
    private static final Set<ServerLog> serverLogsFinished = new TreeSet<ServerLog>();
	
	
    @Override
	public void LoadFile(String filePath){
    	if (filePath!=null || filePath!=""){
	    	final String path=filePath;
	    	final File file =new File(path);
	    	if (file.exists()) {
	    			loadServerLog(file);                 
	    	}else{
	    		LOGGER.error("Log file not found {}",file.getName());
	    		}
	    	}
    }

    @Override
    public ServerInfo addServerInfo(ServerInfo serverInfo) {
    	if (serverInfo.isAlert()) {
    		LOGGER.warn("ALERT [ID: {}  take to long: {}ms] ",serverInfo.getIdServer(),serverInfo.getDuration());
    	}
    	final ServerInfo serverInfoResult = serverInfoRepository.save(serverInfo);
    	LOGGER.debug("Saving ServerInfo...   [RESULT: "+serverInfoResult.toString()+"]");
    	return serverInfoResult;
    }   
    
    @Override
    public List<ServerInfo> getAllServerInfo() {
		List<ServerInfo> list= serverInfoRepository.findAll();
		list.forEach(action->LOGGER.debug("[DB Content line: {}]",action.toString()));     
		return list;
    }

    private void loadServerLog(File file) {
    	LOGGER.info("START: Loading log file: "+file.getName()+ "  startup date: ["+new Date().toString()+"]");
    	try {
    		final LineIterator it = FileUtils.lineIterator(file, StandardCharsets.UTF_8.name());
    		 ExecutorService executor = Executors.newCachedThreadPool();
            while (it.hasNext()) {
                final String line = it.nextLine();
                if (line!="" && line.startsWith("{") && line.endsWith("}") ){
                    final ObjectMapper objectMapper = new ObjectMapper();
                    final ServerLog serverLog=objectMapper.readValue(line, ServerLog.class);
                   
                    if (serverLog.getState()==State.STARTED){
                    	executor.submit(() -> {
                    		alertCheck(serverLog,serverLogsFinished,serverLogsStarted);
                        });
                    }
                    if (serverLog.getState()==State.FINISHED){
                    	executor.submit(() -> {
                            alertCheck(serverLog,serverLogsStarted,serverLogsFinished);  
                        });
                    }
                }
            }
            LOGGER.info("END: Loading log file: "+file.getName()+ "  end date: ["+new Date().toString()+"]");
            it.close();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(),e);
        }
    }

    
    private void alertCheck(ServerLog serverLog, Set<ServerLog> serviceLog1,Set<ServerLog> serviceLog2) {
    	LOGGER.info("alertCheck");
    	for (ServerLog element: serviceLog1){
    		if (serverLog.getId().equals(element.getId())){
    			long duration=Math.abs((long)element.getTimestamp().getTime()-serverLog.getTimestamp().getTime());
    			ServerInfo info= new ServerInfo(serverLog.getId(),duration ,serverLog.getType(), serverLog.getHost(),duration>4 ? true : false);               
    			serviceLog1.remove(element);
    			addServerInfo(info);
    			return;
    		}
    	}
    	serviceLog2.add(serverLog);
    }    

}
