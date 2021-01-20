import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Main extends JPanel {
    private RedBlackTree redBlackTree = new RedBlackTree();
    private RedBlackTreeViewer redBlackTreeViewer = new RedBlackTreeViewer(redBlackTree);
    public Main() {
        redBlackTreeViewer.setBackground(Color.WHITE);
        super.setLayout(new BorderLayout());
        redBlackTreeViewer.setPreferredSize(new Dimension(750, 500));
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(redBlackTreeViewer);
        add(scroll, BorderLayout.CENTER);
        addButtons();
    }
    private void addButtons() {
        final JButton btn_ins = new JButton();
        btn_ins.setText("Add");
        final JButton btn_del = new JButton();
        btn_del.setText("Remove");
        final JButton btn_clr = new JButton();
        btn_clr.setText("Clear");
        JPanel panel = new JPanel();
        panel.add(btn_ins);
        panel.add(btn_del);
        panel.add(btn_clr);
        panel.setBackground(Color.WHITE);
        add(panel, BorderLayout.SOUTH);
        btn_ins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    String input = JOptionPane.showInputDialog("Input");
                    Integer number = Integer.parseInt(input);
                    if (redBlackTree.search(number)) {
                        JOptionPane.showMessageDialog(null, number + " Already exist");
                    } else {
                        redBlackTree.insert(number);
                        redBlackTreeViewer.repaint();
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Input Error!");
                }
            }
        });
        btn_del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String input = JOptionPane.showInputDialog("Input");
                    Integer number = Integer.parseInt(input);
                    if (!redBlackTree.search(number)) {
                        JOptionPane.showMessageDialog(null, "Doesn't Exist");
                    } else {
                        redBlackTree.delete(number);
                        redBlackTreeViewer.repaint();
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Input Error!");
                }
            }
        });
        btn_clr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                redBlackTree.clear();
                redBlackTreeViewer.repaint();
            }
        });
    }
    public static void main(String... args) {
        JFrame j = new JFrame();
        j.add(new Main());
        j.pack();
        j.setVisible(true);
    }
}