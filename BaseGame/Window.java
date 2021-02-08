package Corona2DGamePackage.BaseGame;

import javax.swing.JFrame;

public class Window extends JFrame {
    int windowHeight;
    int windowWidth;
    String windowName;

    public Window(int width, int height, String name) {
        this.windowHeight = height;
        this.windowWidth = width;
        this.windowName = name;
    }

    public void configFrame() {
        this.setSize(this.windowWidth, this.windowHeight);
        this.setTitle(this.windowName);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

    }
}
