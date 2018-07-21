package com.skava.groovy.utils
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import com.jcraft.jsch.UserInfo
import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelExec
import java.util.Properties
public class PuttyConnection {
	static def skcid = "4e8bf33c-95c6-427e-a21b-c8a7077ec960";
	static def domain = "34.192.194.117";
	static def domainusrname = "automation_user1";
	static def domainpwd = "GuPJ2O1L2jtLN0";
	static def logname= "promotion";
	static def logfilename = "promotionlog";
	static def sshPort = "22";
	static def puttySession;
	static def log;

	public static void testConnection() {
		puttySession=SessionObjectFactory.getPuttySession()
		SessionObjectFactory.execCommand("tail -500 /usr/local/skava/logs/"+"$logname"+".log | grep \"${skcid}\" > /media/ephemeral0/automation/"+"$logfilename"+".txt",
			"/media/ephemeral0/automation/"+"$logfilename"+".txt","F:/logs/"+"$logfilename"+".txt")
	}
}