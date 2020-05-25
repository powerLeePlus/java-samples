package pattern.decorator.impl;

import pattern.Sharp;
import pattern.decorator.SharpDecorator;

/**
 * @author lwq
 * @date 2020/5/9 0009
 */
public class RedSharpDecorator extends SharpDecorator {
	public RedSharpDecorator(Sharp decoratedSharp) {
		super(decoratedSharp);
	}

	@Override
	public void draw() {
		decoratedSharp.draw();
		setRedBorder(decoratedSharp);
	}

	private void setRedBorder(Sharp decoratedSharp) {
		System.out.println("Border Color：Red");
	}
}
