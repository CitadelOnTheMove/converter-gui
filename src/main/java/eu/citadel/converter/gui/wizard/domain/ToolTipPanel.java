package eu.citadel.converter.gui.wizard.domain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class ToolTipPanel extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ToolTipPanel(String t)
    {
        setPreferredSize( new Dimension(200, 200) );
        setToolTipText(t);
    }

    public void paintComponent(Graphics g)
    {
        g.setColor( Color.red );
        g.fillRect(0, 0, 100, 200);
        g.setColor( Color.blue );
        g.fillRect(100, 0, 100, 200);
    }

    public Point getToolTipLocation(MouseEvent e)
    {
        Point p = e.getPoint();
        p.y += 15;
        return p;
//      return super.getToolTipLocation(e);
    }
}