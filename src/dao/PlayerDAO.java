package dao;

import java.io.BufferedReader;
import java.net.SocketTimeoutException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import client.service.ClientPacketSender;
import client.ui.ShowErrorPane;
import vo.Packet;
import vo.PlayerVO;

public class PlayerDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(PlayerDAO.class);
	
	public PlayerDAO() { }
	
	public int playerJoin(PlayerVO vo) throws ClassNotFoundException {
		ClientPacketSender.instance.join(vo);
		Packet result = getResponse();
		if(result.getMotion().equals("1"))
			return 1;
		else 
			return 0;
	}//playerJoin()
	
	public PlayerVO login(String id, String pw) {
		ClientPacketSender.instance.login(id, pw);
		Packet result = getResponse();
		if(result.getPlayerVO() != null)
			return result.getPlayerVO();
		else
			return null;
	}//login()
	
	public boolean selectID(String id) {
		ClientPacketSender.instance.selectId(id);
		Packet result = getResponse();
		if(result.getMotion().equals("true")) 
			return true;
		else
			return false;
	}//selectID()
	
	public boolean selectNick(String nick) {
		ClientPacketSender.instance.selectNick(nick);
		Packet result = getResponse();
		if(result.getMotion().equals("true"))
			return true;
		else 
			return false;
	}//selectNick()
	
	public boolean selectMail(String mail) {
		ClientPacketSender.instance.selectMail(mail);
		Packet result = getResponse();
		if(result.getMotion().equals("true"))
			return true;
		else 
			return false;
	}//selectMail()
	
	public boolean sendMailCode(String mail) {
		ClientPacketSender.instance.sendMailCode(mail);
		getResponse();
		return true;
	} //sendMailCode()
	
	public boolean checkMailCode(String code) {
		ClientPacketSender.instance.checkMailCode(code);
		Packet result = getResponse();
		if(result.getMotion().equals("true"))
			return true;
		else 
			return false;
	}//checkMailCode()
	
	public void playerSave(PlayerVO vo) {
		ClientPacketSender.instance.playerSave(vo);
	}//playerSave()
	
	public PlayerVO selectOnePlayerWithNo(int playerNo) {
		ClientPacketSender.instance.selectOnePlayerWithNo(playerNo);
		Packet result = getResponse();
		if(result.getPlayerVO() != null)
			return result.getPlayerVO();
		else
			return null;
	}//selectOnePlayerWithNo()
	
	public Packet getResponse() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			BufferedReader br = PlayerVO.myVO.getBrSocket();
			PlayerVO.myVO.getSocket().setSoTimeout(10000);
			String packetStr = "";
			packetStr = br.readLine();
			logger.debug(packetStr);
			Packet packet = mapper.readValue(packetStr, Packet.class);
			
			return packet;
		} catch (SocketTimeoutException e) {
			new ShowErrorPane("서버가 응답하지 않습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} //getResponse
	
}