package com.lextech.internal.ribbonizer;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

/**
 * User: geoffpowell
 * Date: 12/18/15
 */
public class RibbonFilter implements Consumer<BufferedImage> {

    final Color ribbonColor = new Color(255,255,255,64);

    final Color labelColor = Color.WHITE;

    final Color shadowColor = new Color(0,0,0,200);

    final String fontName = "Arial";

    final int padding = 4;

    final int fontStyle = Font.PLAIN;

    String label;

    public RibbonFilter(String label) {
        this.label = label;
    }

    static void drawString(Graphics2D g, String str, int x, int y) {
        g.drawString(str, x, y);
    }

    @Override
    public void accept(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        Graphics2D g = (Graphics2D) image.getGraphics();

        g.setFont(getFont(height));
        FontMetrics fm = g.getFontMetrics();

        int ribbonInset = width / 10;
        label = ellipsize(label, width, ribbonInset, fm);

        FontRenderContext frc = new FontRenderContext(g.getTransform(), true, true);
        Rectangle2D labelBounds = g.getFont().getStringBounds(label, frc);

        int ribbonHeight = (int) Math.floor(labelBounds.getHeight()) + padding * 2;
        int y = height - ribbonHeight;

        // draw the ribbon
        g.setColor(ribbonColor);
       // g.fillRect(0, y - fm.getDescent(), width, ribbonHeight + fm.getDescent());
        g.fillPolygon(getRibbon(width, y, ribbonHeight));

        // draw the label
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g.setColor(shadowColor);
        drawString(g, label, (int)
                (width - Math.floor(labelBounds.getWidth()))/2 + 2, height - fm.getDescent() - padding + 2);

        g.setColor(labelColor);
        drawString(g, label, (int)
                (width - Math.floor(labelBounds.getWidth()))/2, height - fm.getDescent() - padding);

        g.dispose();
    }

    String ellipsize(String string, int width, int ribbonInset, FontMetrics fontMetrics) {
        int maxCharWidth = fontMetrics.charWidth('d');
        int maxWidthAvailable = width - ((ribbonInset + padding) * 2);
        int maxStringLength = maxWidthAvailable / maxCharWidth;
        if (maxStringLength <= 0 || string.length() <= maxStringLength || fontMetrics.stringWidth(string) <= maxWidthAvailable) {
            return string;
        } else {
            return string.substring(0, maxStringLength - 3) + "...";
        }
    }

    Polygon getRibbon(int width, int y, int ribbonHeight) {
        int ribbonInset = width / 10;
        int[] xs = new int[] { 0, width, width - ribbonInset, width, 0, ribbonInset};
        int[] ys = new int[] { y, y, y + ribbonHeight/2,  y + ribbonHeight, y + ribbonHeight, y + ribbonHeight/2};
        return new Polygon(xs, ys, 6);
    }

    Font getFont(int height) {
        return new Font(fontName, fontStyle, height / 6);
    }
}
