package decorate;

public class IPhone implements Phone {
	@Override
	public void call() {
		System.out.println("使用苹果手机打电话");
	}

}
