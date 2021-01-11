package pattern.decorator;


import pattern.Sharp;

/**
 * @author lwq
 * @date 2020/5/9 0009
 */
public abstract class SharpDecorator implements Sharp {
	protected Sharp decoratedSharp;

	public SharpDecorator(Sharp decoratedSharp) {
		this.decoratedSharp = decoratedSharp;
	}

	@Override
	public void draw() {
		decoratedSharp.draw();
	}
}
