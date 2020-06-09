package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Protocol;
import vo.Packet;
import vo.PlayerVO;

public class ReceiveClientPacket extends Thread { // Server

	private Socket socket;
	private PlayerVO thisPlayerVO;
	public static ArrayList<PlayerVO> playerList = new ArrayList<PlayerVO>();
	private ObjectMapper mapper = new ObjectMapper();
	private RoomOperator ro = RoomOperator.getRoomOperator();
	public ReceiveClientPacket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			try {
				while (true) {
					String packetStr = br.readLine();
//					System.out.println(packetStr);
					Packet packet = mapper.readValue(packetStr, Packet.class);
					try {
						analysisPacket(packet); // action에 따라서 동작 실행
					} catch (Exception e) {
						e.printStackTrace();
					}
				} // while
			} catch (SocketException e) {
//				this.exitPlayer(thisPlayerVO);
			} // try~catch

		} catch (IOException e) {
			e.printStackTrace();
		} // try~catch
	} // run()

	public void analysisPacket(Packet packet) throws JsonProcessingException {

		switch (packet.getAction()) {
		case Protocol.MESSAGE:
			for (int j = 0; j < playerList.size(); j++) {
				if (packet.getPlayerVO().getLocation().equals(playerList.get(j).getLocation())) {
					PrintWriter pw = playerList.get(j).getPwSocket();
					pw.println(mapper.writeValueAsString(packet));
				} // if
			} // for

			break;

		case Protocol.CHANGELOCATION:

			if (thisPlayerVO == null) {
				thisPlayerVO = packet.getPlayerVO();
				thisPlayerVO.setSocketWithBrPw(socket);
				playerList.add(thisPlayerVO);

			} else {
				//방 입실 및 퇴실 처리 브로드케스트 
				Packet enterPacket = packet;
				enterPacket.setAction(Protocol.EXITPLAYER); // 방에 입장시 사람들에게 알림
				Packet exitPacket = packet; // 방에서 나간곳에 있는 사람들에게 알림
				exitPacket.setAction(Protocol.JOINPLAYER);
				String beforeLoction = thisPlayerVO.getLocation();
				thisPlayerVO.setLocation(packet.getPlayerVO().getLocation());

				
				for (int j = 0; j < playerList.size(); j++) {
					if (playerList.get(j).getNo() != thisPlayerVO.getNo()) { //자기 자신에게는 자신의 이동정보를 안보냄
						ArrayList<PlayerVO> loctionPlayerList = new ArrayList<>();
						for (int i = 0; i < playerList.size(); i++) {
							
							if (packet.getPlayerVO().getLocation().equals(playerList.get(i).getLocation())) {
								loctionPlayerList.add(playerList.get(i));
							} // if
						} // for
						
						if(packet.getPlayerVO().getLocation().equals(Protocol.LOBBY)){
							
						}
					}
						if (packet.getPlayerVO().getLocation().equals(playerList.get(j).getLocation())) {

							PrintWriter pw = playerList.get(j).getPwSocket();
							if(packet.getPlayerVO().getLocation().equals(Protocol.LOBBY)) {
								
							} else {
								
							}
							pw.println(mapper.writeValueAsString(enterPacket));

						} else if (beforeLoction.equals(playerList.get(j).getLocation())) {

							PrintWriter pw = playerList.get(j).getPwSocket();
							pw.println(mapper.writeValueAsString(exitPacket));

						} // if~else
				} //for
			} //if~else
			break;

		case Protocol.MAKEROOM:
			
			
		case Protocol.LISTROOMPLAYER:
			packet.getPlayerVO().getPwSocket().println(mapper);
			
			break;
			
		} // switch
	} // runMainGame

	public void exitPlayer(PlayerVO vo) {
		System.err.println(thisPlayerVO.getNic() + "님이 나가셨습니다.");
		for (int j = 0; j < playerList.size(); j++) {
			if (playerList.get(j).getNo() == thisPlayerVO.getNo())
				playerList.remove(j);
		}
	}

	public void localBroadcasting(Packet packet) throws JsonProcessingException {
		for (int i = 0; i < playerList.size(); i++) {
			if (packet.getPlayerVO().getLocation().equals(playerList.get(i).getLocation()))
				playerList.get(i).getPwSocket().println(mapper.writeValueAsString(packet));
		}
	}

} // ReceiveClientPacket