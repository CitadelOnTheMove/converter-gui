package eu.citadel.converter.gui.wizard.domain;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.citadel.converter.gui.wizard.localization.Messages;

@SuppressWarnings("serial")
public class RoundButton extends JButton {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(RoundButton.class);
	
	public RoundButton(Icon icon) {
		this(null, icon);
		logger.trace("RoundButton(Icon) - start");

		logger.trace("RoundButton(Icon) - end");
	}
	
	public RoundButton(String text) {
		this(text, null);
		logger.trace("RoundButton(String) - start");

		logger.trace("RoundButton(String) - end");
	}
	
	public RoundButton(String text, Icon icon) {
		logger.trace("RoundButton(String, Icon) - start");

		setModel(new DefaultButtonModel());
		init(text, icon);
		if(icon==null) {
			logger.trace("RoundButton(String, Icon) - end");
			return;
		}
		setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
		setBackground(Color.BLACK);
		setContentAreaFilled(false);
		setFocusPainted(false);
		//setVerticalAlignment(SwingConstants.TOP);
		setAlignmentY(Component.TOP_ALIGNMENT);
		initShape();

		logger.trace("RoundButton(String, Icon) - end");
	}
	public RoundButton(Icon ic, String translationKey) {
		this(ic);
		logger.trace("RoundButton(Icon, String) - start");

		this.setToolTipText(Messages.getString(translationKey, translationKey));

		logger.trace("RoundButton(Icon, String) - end");
	}
	protected Shape shape, base;
	protected void initShape() {
		logger.trace("initShape() - start");

		if(!getBounds().equals(base)) {
			Dimension s = getPreferredSize();
			base = getBounds();
			shape = new Ellipse2D.Float(0, 0, s.width-1, s.height-1);
		}

		logger.trace("initShape() - end");
	}
	@Override public Dimension getPreferredSize() {
		logger.trace("getPreferredSize() - start");

		Icon icon = getIcon();
		Insets i = getInsets();
		int iw = Math.max(icon.getIconWidth(), icon.getIconHeight());
		Dimension returnDimension = new Dimension(iw + i.right + i.left, iw + i.top + i.bottom);
		logger.trace("getPreferredSize() - end");
		return returnDimension;
	}
	@Override protected void paintBorder(Graphics g) {
		logger.trace("paintBorder(Graphics) - start");

		/*initShape();
	    Graphics2D g2 = (Graphics2D)g;
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                        RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setColor(getBackground());
	    //g2.setStroke(new BasicStroke(1.0f));
	    g2.draw(shape);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                        RenderingHints.VALUE_ANTIALIAS_OFF);*/

		logger.trace("paintBorder(Graphics) - end");
	}
	@Override public boolean contains(int x, int y) {
		logger.trace("contains(int, int) - start");

		initShape();
		boolean returnboolean = shape.contains(x, y);
		logger.trace("contains(int, int) - end");
		return returnboolean;
		//or return super.contains(x, y) && ((image.getRGB(x, y) >> 24) & 0xff) > 0;
	}
}