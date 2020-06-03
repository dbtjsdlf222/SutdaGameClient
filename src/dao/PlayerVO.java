package dao;

public class PlayerVO {
   String ID;
   String PW;
   String NICKNAME;
   
   public PlayerVO(String id, String pw, String nick){
      this.ID = id;
      this.PW = pw;
      this.NICKNAME = nick;
   }

   public String getID() {
      return ID;
   }

   public void setID(String id) {
      ID = id;
   }

   public String getPW() {
      return PW;
   }

   public void setPW(String pw) {
      PW = pw;
   }

   public String getNICKNAME() {
      return NICKNAME;
   }

   public void setNICKNAME(String nick) {
      NICKNAME = nick;
   }
   
   
}