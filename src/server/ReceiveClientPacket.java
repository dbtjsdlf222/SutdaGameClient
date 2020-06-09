package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vo.Protocol;
import vo.Room;
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
					System.out.println(packetStr);
					Packet packet = mapper.readValue(packetStr, Packet.class);
					try {
						analysisPacket(packet); // action에 따라서 동작 실행
					} catch (Exception e) {
						e.printStackTrace();
					}
				} // while
			} catch (SocketException e) {
				this.exitPlayer(thisPlayerVO);
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
			}
				//방 입실 및 퇴실 처리 브로드케스트 
				Packet joinPacket = packet;
				joinPacket.setAction(Protocol.JOINPLAYER); // 방에 입장시 사람들에게 알림
				Packet exitPacket = packet; // 방에서 나간곳에 있는 사람들에게 알림
				exitPacket.setAction(Protocol.EXITPLAYER);
				String beforeLoction = thisPlayerVO.getLocation();
				thisPlayerVO.setLocation(packet.getPlayerVO().getLocation());
				
				ArrayList<PlayerVO> localPlayerList = new ArrayList<>();
				
				for (int j = 0; j < playerList.size(); j++) {
					//자신의 소켓을 제외함
					if (playerList.get(j).getNo() == thisPlayerVO.getNo()) {
						localPlayerList.add(packet.getPlayerVO());
						playerList.get(j).setLocation(packet.getPlayerVO().getLocation());
						continue;
					}
						
						if (packet.getPlayerVO().getLocation().equals(playerList.get(j).getLocation())) {
							
							localPlayerList.add(playerList.get(j));
							
							playerList.get(j).getPwSocket().println(mapper.writeValueAsString(joinPacket));

						} else if (beforeLoction.equals(playerList.get(j).getLocation())) {
							//나간 방안에 사람들한테 나간 쿼리 보냄
							playerList.get(j).getPwSocket().println(mapper.writeValueAsString(exitPacket));

						} // if~else
						
				} //for
				if(packet.getPlayerVO().getLocation().equals(Protocol.ROOM)) {
					packet.setRoom(ro.getAllRoom());
				}
				packet.setPlayerList(localPlayerList);
				thisPlayerVO.getPwSocket().println(mapper.writeValueAsString(packet));
				
			break;

		case Protocol.MAKEROOM:
			ro.makeRoom(packet.getPlayerVO());
			
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