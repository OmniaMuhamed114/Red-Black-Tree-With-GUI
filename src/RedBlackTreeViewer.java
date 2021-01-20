import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;


public class RedBlackTreeViewer extends JPanel {
    private RedBlackTree redBlackTree;
    public RedBlackTreeViewer(RedBlackTree redBlackTree) {
        this.redBlackTree = redBlackTree;
    }
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (!redBlackTree.isEmpty()) {
            paintTree((Graphics2D) graphics, redBlackTree.getRoot(), getWidth() / 2, 30, 1);
        }
    }
    private void paintTree(Graphics2D g, Node root, int x, int y, int level) {
        int offset = redBlackTree.getDepth() * 28/level;
        drawNode(g, root, x, y);
        if (root.left != null) {
            join(g, x - offset, y + 50, x, y);
            paintTree(g, root.left, x - offset, y + 50, level+1);
        }
        if (root.right != null) {
            join(g, x + offset, y + 50, x, y);
            paintTree(g, root.right, x + offset, y + 50, level+1);
        }
    }
    private void drawNode(Graphics2D g, Node node, int x, int y) {
        g.setColor(node.color);
        g.fillOval(x - 18, y - 18, 36, 36);
        g.setColor(Color.WHITE);
        String text = node.value == 0 ? "NULL" : node.value+"" ;
        double textWidth = g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, (int) (x - textWidth / 2), y + g.getFontMetrics().getMaxAscent() / 2);
        g.setColor(Color.GRAY);
    }
    private void join(Graphics2D g, int x1, int y1, int x2, int y2) {
        double hypot = Math.hypot(50, x2 - x1);
        int x11 = (int) (x1 + 18 * (x2 - x1) / hypot);
        int y11 = (int) (y1 - 18 * 50 / hypot);
        int x21 = (int) (x2 - 18 * (x2 - x1) / hypot);
        int y21 = (int) (y2 + 18 * 50 / hypot);
        g.drawLine(x11, y11, x21, y21);
    }
}
