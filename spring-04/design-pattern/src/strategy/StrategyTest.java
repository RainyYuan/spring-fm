package strategy;

import org.junit.Test;

/**
 * 测试类
 * @author think
 *
 */
public class StrategyTest {

	@Test
	public void test() {
		TravelStrategy strategy = new AirPlanelStrategy() ;
		PersonContext person = new PersonContext(strategy);
		person.travel();
		
		strategy = new TrainStrategy();
		person.setStrategy(strategy);
		person.travel();
		
		strategy = new BicycleStrategy();
		person.setStrategy(strategy);
		person.travel();
	}
}
