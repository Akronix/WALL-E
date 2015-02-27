package tp.pr5.items.testprofesor;

import tp.pr5.items.Item;
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

public class MockItem extends Item {
	public static final String DEF_NAME = "MockItem";
	public static final String DEF_DESC = "MockItem description";
	public static final String WRONG_NAME = "anotherMockItem";
	public static final int DEF_VALUE = 10;
	public static final int DEF_TIMES = 1;
	
	public MockItem(String id, String description) {
		super(id, description);
	}

	public MockItem() {
		this(MockItem.DEF_NAME, MockItem.DEF_DESC);
	}

	public MockItem(String name) {
		this(name, MockItem.DEF_DESC);
	}

	@Override
	public
	boolean canBeUsed() {
		return true;
	}

	@Override
	public boolean use(RobotEngine r, NavigationModule nav) {
		return true;
	}

}
