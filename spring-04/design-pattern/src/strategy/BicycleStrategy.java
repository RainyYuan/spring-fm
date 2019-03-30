package strategy;
/***
 * 具体策略类(ConcreteStrategy)
 * @author think
 *
 */
public class BicycleStrategy implements TravelStrategy {

	@Override
	public void travelWay() {
		System.out.println("旅游方式选择：骑自行车");
	}

}
