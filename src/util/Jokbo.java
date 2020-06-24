package util;

import java.util.ArrayList;

class Card{
	private float card1;
	private float card2;
	public Card(float card1, float card2) {
		super();
		this.card1 = card1;
		this.card2 = card2;
	}
	
}

public class Jokbo {
	private Jokbo jokbo;
	private ArrayList<Jokbo> jokboList;
	private String name;
	private ArrayList<Card> cardComboArr;
	
	private Jokbo() {
		//생성자가 실행되면 모든 족보 add
		jokboList = new ArrayList<Jokbo>();
		cardComboArr = new ArrayList<>();
//		jokboList.add(new Jokbo(3,8,"38광땡"));
//		jokboList.add(new Jokbo(1,8,"18광땡"));
//		jokboList.add(new Jokbo(1,3,"13광땡"));
//		for(float i=1; i<11; i++) {
//			jokboList.add(new Jokbo(i, (float) (i+0.5), i+"땡"));
//			if(i==10)
//				jokboList.add(new Jokbo(10, (float) 10.5, "장땡"));
//		}
		
		//알리
		Jokbo a = new Jokbo("알리");
		a.cardComboArr.add(new Card(1,2));
		a.cardComboArr.add(new Card(1,2.5f));
		a.cardComboArr.add(new Card(1.5f,2));
		a.cardComboArr.add(new Card(1.5f,2.5f));
		jokboList.add(a);
		//독사
		Jokbo b = new Jokbo("독사");
		b.cardComboArr.add(new Card(1,4));
		b.cardComboArr.add(new Card(1,4.5f));
		b.cardComboArr.add(new Card(1.5f,4));
		b.cardComboArr.add(new Card(1.5f,4.5f));
		jokboList.add(b);
		//구삥
		Jokbo c = new Jokbo("구삥");
		c.cardComboArr.add(new Card(1,9));
		c.cardComboArr.add(new Card(1,9.5f));
		c.cardComboArr.add(new Card(1.5f,9));
		c.cardComboArr.add(new Card(1.5f,9.5f));
		jokboList.add(c);
		//장삥
		Jokbo d = new Jokbo("장삥");
		d.cardComboArr.add(new Card(1,10));
		d.cardComboArr.add(new Card(1,10.5f));
		d.cardComboArr.add(new Card(1.5f,10));
		d.cardComboArr.add(new Card(1.5f,10.5f));
		jokboList.add(d);
		//장사
		Jokbo e = new Jokbo("장사");
		e.cardComboArr.add(new Card(4,10));
		e.cardComboArr.add(new Card(4,10.5f));
		e.cardComboArr.add(new Card(4.5f,10));
		e.cardComboArr.add(new Card(4.5f,10.5f));
		jokboList.add(e);
		//세륙
		Jokbo f = new Jokbo("세륙");
		f.cardComboArr.add(new Card(4,6));
		f.cardComboArr.add(new Card(4,6.5f));
		f.cardComboArr.add(new Card(4.5f,6));
		f.cardComboArr.add(new Card(4.5f,6.5f));
		jokboList.add(f);
		
	}

	public Jokbo(String name) {
		this.name = name;
	}



	public static Jokbo getInstance() {
		if(jokbo==null) { jokbo = new Jokbo(); }
		return jokbo;
	}
	
	public ArrayList<Jokbo> getJokboList() { return jokboList; }
	public void setJokboList(ArrayList<Jokbo> jokboList) { this.jokboList = jokboList; }
	public float getCard1() { return card1; }
	public void setCard1(float card1) { this.card1 = card1; }
	public float getCard2() { return card2; }
	public void setCard2(float card2) { this.card2 = card2; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
} //class
