package com.skava.groovy.utils
import com.jcraft.jsch.*;
import javax.naming.spi.ObjectFactory;
//import org.springframework.beans.factory.ObjectFactory;
class SessionObjectFactory {
	public static void main(String[] args) throws JSchException, SftpException{

	}

	private static final String EXEC_STR = "exec";
	private static final String SFTP_STR = "sftp";

	//Update Session with original class required to create the putty session object
	private static Session puttySession;
	private static ChannelExec channel;

	public static Session getPuttySession() throws JSchException {
		if(null == puttySession) {
			synchronized(SessionObjectFactory.class) {
					if(puttySession == null) {
					//log.info("Creating Session")
					System.out.println("Creating Session");
							java.util.Properties config = new java.util.Properties();
							config.put ("StrictHostKeyChecking", "no");
							JSch ssh = new JSch();
							puttySession = ssh.getSession( "automation_user1", "34.192.194.117", 22);
							puttySession.setPassword("GuPJ2O1L2jtLN0");
							puttySession.setConfig(config);
							puttySession.connect();

					}
			}
		}
		return puttySession;
	}

	public static void execCommand(String commandStr, String fromSftpfilePath, String toSftpfilePath) throws JSchException, SftpException {
		if (null != commandStr && commandStr.trim().length() > 0) {
			synchronized(SessionObjectFactory.class) {
				puttySession = SessionObjectFactory.getPuttySession();
				ChannelExec commandExecutor = null;

				try {
					commandExecutor = (ChannelExec) puttySession.openChannel(SessionObjectFactory.EXEC_STR);

					commandExecutor.setCommand(commandStr);
					commandExecutor.connect();
				}
				finally {
					if (null != commandExecutor) {
						commandExecutor.disconnect();
					}
				}

				ChannelSftp sftpChannel = null;

				try {
					sftpChannel = (ChannelSftp) puttySession.openChannel(SessionObjectFactory.SFTP_STR);
					sftpChannel.connect();
					sftpChannel.get(fromSftpfilePath, toSftpfilePath);

				}
				catch (Exception e){
					e.printStackTrace();
				}
				finally {
					if (null != sftpChannel) {
						sftpChannel.disconnect();
					}
				}
			}
		}
	}

	public static void close(){
		if(null != puttySession){
			synchronized(SessionObjectFactory.class) {
				if(null != puttySession) {
					System.out.println("Closing Session");
					puttySession.disconnect();
					puttySession = null;
				}
			}
		}
	}
}