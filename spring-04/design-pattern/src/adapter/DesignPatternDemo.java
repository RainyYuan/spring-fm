package adapter;

public class DesignPatternDemo {

	public static void main(String[] args) {
		DeGuoHotel hotel = new DeGuoHotel();
//		hotel.setDBSocket(new DBSocketAdapter(new DBSocketImpl()));
		hotel.setDBSocket(new GBSocketAdapter(new GBSocketImpl()));
		hotel.charge();
	}

}
