package client;import Server.Accept;public class Main{  public static final int SCREEN_WIDTH = 1280;  public static final int SCREEN_HEIGHT = 720;    public static void main(String[] args)  {    Thread th = new Thread(new Accept());    th.start();  }}