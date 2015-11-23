package unit4_exercise18_multithreading;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RectangleCollision extends JFrame {

	private static final int MOVE_AMOUNT = 3;
	private static final int SQUARE_SIZE = 50;

	public static void main(String[] args) {
		new RectangleCollision();
	}

	private RectangleCollision() {
		super("Rectangle Collision");
		this.setPreferredSize(new Dimension(500, 500));
		this.setLocation(100, 100);
		this.add(new ContentPanel());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.pack();
	}

	private class ContentPanel extends JPanel {

		private Rectangle userRect;
		private ArrayList<BoxAI> boxArray = new ArrayList<BoxAI>();

		private ContentPanel() {
			userRect = new Rectangle(
					(int) (Math.random() * RectangleCollision.this.getWidth()),
					(int) (Math.random() * RectangleCollision.this.getHeight()),
					SQUARE_SIZE, SQUARE_SIZE);
			boxArray.add(new BoxAI());
			this.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// nothing
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// nothing
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_DOWN)
						userRect.y += MOVE_AMOUNT;
					else if (e.getKeyCode() == KeyEvent.VK_UP)
						userRect.y -= MOVE_AMOUNT;
					else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
						userRect.x += MOVE_AMOUNT;
					else if (e.getKeyCode() == KeyEvent.VK_LEFT)
						userRect.x -= MOVE_AMOUNT;
					else if (e.getKeyCode() == KeyEvent.VK_O) {
						boxArray.add(new BoxAI());
					}
					repaint();
				}
			});
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.RED);
			g.fillRect(userRect.x, userRect.y, userRect.width, userRect.height);

			g.setColor(Color.GRAY);
			for (int i = 0; i < boxArray.size(); i++) {
				g.fillRect(boxArray.get(i).x, boxArray.get(i).y,
						boxArray.get(i).width, boxArray.get(i).height);
			}
		}

		private class BoxAI extends Rectangle {

			private static final int MOVE_MULTIPLIER = 100;
			private static final int SMOOTHNESS = 100;
			private static final double SPEED = 1.0;

			private BoxAI() {
				super(
						(int) (Math.random() * RectangleCollision.this
								.getWidth()),
						(int) (Math.random() * RectangleCollision.this
								.getHeight()), SQUARE_SIZE, SQUARE_SIZE);
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						while (true) {

							int deltaX = (int) (Math.random() * MOVE_MULTIPLIER - (MOVE_MULTIPLIER / 2));
							int deltaY = (int) (Math.random() * MOVE_MULTIPLIER - (MOVE_MULTIPLIER / 2));

							while (BoxAI.this.x + deltaX < 0
									|| BoxAI.this.x + deltaX > RectangleCollision.this
											.getWidth())
								deltaX = (int) (Math.random() * MOVE_MULTIPLIER - (MOVE_MULTIPLIER / 2));
							while (BoxAI.this.y + deltaY < 0
									|| BoxAI.this.y + deltaY > RectangleCollision.this
											.getHeight())
								deltaY = (int) (Math.random() * MOVE_MULTIPLIER - (MOVE_MULTIPLIER / 2));

							for (int step = 0; step < SMOOTHNESS; step++) {

								try {
									Thread.sleep(50);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								ContentPanel.this.repaint();
							}

						}
					}
				});
				t.start();
			}
		}
	}
}
