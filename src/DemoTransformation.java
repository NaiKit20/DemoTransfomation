import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DemoTransformation extends JFrame {

    public DemoTransformation() {
        DrawImage panel = new DrawImage();
        add(panel);
        setLayout(null);
    }

    public static void main(String[] args) throws Exception {
        DemoTransformation f = new DemoTransformation();
        f.setSize(750, 750);
        f.setVisible(true);
        f.setTitle("กรวิชญ์ เป็นคนทามมมมมมมมม");
    }
}

class DrawImage extends JPanel implements ActionListener {

    JButton btscale, btreflect, btshear, btclear;
    int x1 = 100, y1 = 250, x2 = 200, y2 = 250, x3 = 200, y3 = 100;
    int x1new, y1new, x2new, y2new, x3new, y3new;
    int mouseX;
    int mouseY;

    int ev = 0;
    boolean clicked = false;

    public DrawImage() {
        setSize(750, 750);
        setLayout(null);
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                double area = 0.5 * (-y2 * x3 + y1 * (-x2 + x3) + x1 * (y2 - y3) + x2 * y3);
                double s = 1 / (2 * area) * (y1 * x3 - x1 * y3 + (y3 - y1) * mouseX + (x1 - x3) * mouseY);
                double t = 1 / (2 * area) * (x1 * y2 - y1 * x2 + (y1 - y2) * mouseX + (x2 - x1) * mouseY);

                if (s > 0 && t > 0 && 1 - s - t > 0) {
                    clicked = true;
                } else {
                    if (clicked) {
                        ev = 2;
                        repaint();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        JToolBar toolbar = new JToolBar();
        btscale = new JButton("Scale");
        btscale.addActionListener(this);
        btreflect = new JButton("Reflect");
        btreflect.addActionListener(this);
        btshear = new JButton("Shearing");
        btshear.addActionListener(this);
        btclear = new JButton("Clear");
        btclear.addActionListener(this);

        toolbar.add(btscale);
        toolbar.add(btreflect);
        toolbar.add(btshear);
        toolbar.add(btclear);
        toolbar.setBounds(0, 0, 750, 40);

        add(toolbar);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        drawTri(g);

        if (ev == 1) {
            g.setColor(Color.BLUE);
            scaling(g);
        } else if (ev == 2) {
            g.setColor(Color.BLUE);
            trans(g);
        } else if (ev == 3) {
            g.setColor(Color.BLUE);
            g.translate(x1*6, 0);
            reflec(g);
        } else if(ev == 4) {
            g.setColor(Color.BLUE);
            shear(g);
        }

    }

    // วาดเส้น
    public void ddaLine(Graphics g, int x1, int y1, int x2, int y2) {
        double dx, dy;
        double x, y;
        dx = x2 - x1;
        dy = y2 - y1;
        double m = dx / dy;
        double step;
        double xinc, yinc;

        if (Math.abs(dx) > Math.abs(dy))
            step = Math.abs(dx);
        else
            step = Math.abs(dy);

        xinc = (dx / step);
        yinc = (dy / step);
        x = x1;
        y = y1;

        g.fillOval((int) x, (int) y, 2, 2);

        for (float i = 0; i < step; i++) {
            x = x + xinc;
            y = y + yinc;
            g.fillOval((int) x, (int) y, 2, 2);
        }
    }

    // วาดสามเหลี่ยม
    void drawTri(Graphics g) {
        // x1=100,y1=250,x2=200,y2=250,x3=200,y3=100;
        ddaLine(g, x1, y1, x2, y2);
        ddaLine(g, x1, y1, x3, y3);
        ddaLine(g, x2, y2, x3, y3);
    }

    // ขยายภาพ
    void scaling(Graphics g) {
        int sx = 3, sy = 2;

        x1new = scaleX(x1, sx);
        y1new = scaleY(y1, sy);
        x2new = scaleX(x2, sx);
        y2new = scaleY(y2, sy);
        x3new = scaleX(x3, sx);
        y3new = scaleY(y3, sy);
        ddaLine(g, x1new, y1new, x2new, y2new);
        ddaLine(g, x1new, y1new, x3new, y3new);
        ddaLine(g, x2new, y2new, x3new, y3new);

        System.out.println(x1new);
        System.out.println(y1new);
        System.out.println(x2new);
        System.out.println(y2new);
        System.out.println(x3new);
        System.out.println(y3new);
    }

    int scaleX(int x, int sx) {
        return x * sx;
    }

    int scaleY(int y, int sy) {
        return y * sy;
    }

    // เคลื่อนที่ภาพ
    void trans(Graphics g) {
        int Tx = Tx(x1, mouseX);
        int Ty = Ty(y1, mouseY);

        x1new = x1 + Tx;
        y1new = y1 + Ty;
        x2new = x2 + Tx;
        y2new = y2 + Ty;
        x3new = x3 + Tx;
        y3new = y3 + Ty;
        ddaLine(g, x1new, y1new, x2new, y2new);
        ddaLine(g, x1new, y1new, x3new, y3new);
        ddaLine(g, x2new, y2new, x3new, y3new);

        System.out.println(x1new);
        System.out.println(y1new);
        System.out.println(x2new);
        System.out.println(y2new);
        System.out.println(x3new);
        System.out.println(y3new);
    }

    int Tx(int x, int xnew) {
        return xnew - x;
    }

    int Ty(int y, int ynew) {
        return ynew - y;
    }

    // สะท้อนภาพ
    void reflec(Graphics g) {
        x1new=x1*-1;
        x2new=x2*-1;
        x3new=x3*-1;
        ddaLine(g, x1new, y1, x2new, y2);
        ddaLine(g, x1new, y1, x3new, y3);
        ddaLine(g, x2new, y2, x3new, y3);
    }

    // บิดภาพ
    void shear(Graphics g) {
        y1new=y1+2*x1;
        y2new=y2+2*x2;
        y3new=y3+2*x3;
        ddaLine(g, x1, y1new, x2, y2new);
        ddaLine(g, x1, y1new, x3, y3new);
        ddaLine(g, x2, y2new, x3, y3new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btscale) {
            ev = 1;
            repaint();
        } else if (e.getSource() == btclear) {
            ev = 0;
            repaint();
        } else if (e.getSource() == btreflect) {
            ev = 3;
            repaint();
        } else if (e.getSource() == btshear) {
            ev = 4;
            repaint();
        }
    }
}
