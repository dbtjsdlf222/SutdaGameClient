package util;

import java.util.ArrayList;

class Card {
	private float card1;
	private float card2;

	public Card(float card1, float card2) {
		this.card1 = card1;
		this.card2 = card2;
	}
}

public class Jokbo {
	private static Jokbo instance;
	private ArrayList<Jokbo> jokboList;
	private String name;
	private ArrayList<Card> cardComboArr;

	private Jokbo() {
		// 생성자가 실행되면 모든 족보 add
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

		// 38광땡
		Jokbo a = new Jokbo("38광땡");
		a.cardComboArr.add(new Card(3, 8));
		jokboList.add(a);
		// 18광땡
		Jokbo b = new Jokbo("18광땡");
		b.cardComboArr.add(new Card(1, 8));
		jokboList.add(b);
		// 13광땡
		Jokbo c = new Jokbo("13광땡");
		c.cardComboArr.add(new Card(1, 3));
		jokboList.add(c);
		// 장땡
		Jokbo da = new Jokbo("장땡");
		da.cardComboArr.add(new Card(10,10.5f));
		jokboList.add(da);
		// 9땡
		Jokbo db = new Jokbo("9땡");
		db.cardComboArr.add(new Card(9,9.5f));
		jokboList.add(db);
		// 8땡
		Jokbo dc = new Jokbo("8땡");
		dc.cardComboArr.add(new Card(8,8.5f));
		jokboList.add(dc);
		// 7땡
		Jokbo dd = new Jokbo("7땡");
		dd.cardComboArr.add(new Card(7,7.5f));
		jokboList.add(dd);
		// 6땡
		Jokbo de = new Jokbo("6땡");
		de.cardComboArr.add(new Card(6,6.5f));
		jokboList.add(de);
		// 5땡
		Jokbo df = new Jokbo("5땡");
		df.cardComboArr.add(new Card(5,5.5f));
		jokboList.add(df);
		// 4땡
		Jokbo dg = new Jokbo("4땡");
		dg.cardComboArr.add(new Card(4,4.5f));
		jokboList.add(dg);
		// 3땡
		Jokbo dh = new Jokbo("3땡");
		dh.cardComboArr.add(new Card(3,3.5f));
		jokboList.add(dh);
		// 2땡
		Jokbo di = new Jokbo("2땡");
		di.cardComboArr.add(new Card(2,2.5f));
		jokboList.add(di);
		// 1땡
		Jokbo dj = new Jokbo("1땡");
		dj.cardComboArr.add(new Card(1,1.5f));
		jokboList.add(dj);
		// 알리
		Jokbo e = new Jokbo("알리");
		e.cardComboArr.add(new Card(1, 2));
		e.cardComboArr.add(new Card(1, 2.5f));
		e.cardComboArr.add(new Card(1.5f, 2));
		e.cardComboArr.add(new Card(1.5f, 2.5f));
		jokboList.add(e);
		// 독사
		Jokbo f = new Jokbo("독사");
		f.cardComboArr.add(new Card(1, 4));
		f.cardComboArr.add(new Card(1, 4.5f));
		f.cardComboArr.add(new Card(1.5f, 4));
		f.cardComboArr.add(new Card(1.5f, 4.5f));
		jokboList.add(f);
		// 구삥
		Jokbo g = new Jokbo("구삥");
		g.cardComboArr.add(new Card(1, 9));
		g.cardComboArr.add(new Card(1, 9.5f));
		g.cardComboArr.add(new Card(1.5f, 9));
		g.cardComboArr.add(new Card(1.5f, 9.5f));
		jokboList.add(g);
		// 장삥
		Jokbo h = new Jokbo("장삥");
		h.cardComboArr.add(new Card(1, 10));
		h.cardComboArr.add(new Card(1, 10.5f));
		h.cardComboArr.add(new Card(1.5f, 10));
		h.cardComboArr.add(new Card(1.5f, 10.5f));
		jokboList.add(h);
		// 장사
		Jokbo i = new Jokbo("장사");
		i.cardComboArr.add(new Card(4, 10));
		i.cardComboArr.add(new Card(4, 10.5f));
		i.cardComboArr.add(new Card(4.5f, 10));
		i.cardComboArr.add(new Card(4.5f, 10.5f));
		jokboList.add(i);
		// 세륙
		Jokbo j = new Jokbo("세륙");
		j.cardComboArr.add(new Card(4, 6));
		j.cardComboArr.add(new Card(4, 6.5f));
		j.cardComboArr.add(new Card(4.5f, 6));
		j.cardComboArr.add(new Card(4.5f, 6.5f));
		jokboList.add(j);
		// 땡잡이
		Jokbo k = new Jokbo("땡잡이");
		k.cardComboArr.add(new Card(3, 7));
		jokboList.add(k);
		// 암행어사
		Jokbo l = new Jokbo("암행어사");
		l.cardComboArr.add(new Card(4, 7));
		jokboList.add(l);
		// 구사 재경기
		Jokbo m = new Jokbo("구사");
		m.cardComboArr.add(new Card(4, 9.5f));
		m.cardComboArr.add(new Card(4.5f, 9));
		m.cardComboArr.add(new Card(4.5f, 9.5f));
		jokboList.add(m);
		// 멍텅구리 구사
		Jokbo n = new Jokbo("멍텅구리 구사");
		n.cardComboArr.add(new Card(4,9));
		jokboList.add(n);
	}

	public static Jokbo getInstance() {
		if (instance == null) {
			instance = new Jokbo();
		}
		return instance;
	}

	public ArrayList<Jokbo> getJokboList() {
		return jokboList;
	}

	public Jokbo(String name) {
		this.name = name;
	}

	public void setJokboList(ArrayList<Jokbo> jokboList) {
		this.jokboList = jokboList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

} // class
