package unit4_exercise18_multithreading;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import quicktime.app.event.MouseMoveListener;
import quicktime.app.event.QTMouseEvent;

public class RectangleCollision extends JFrame {

	private static final int MOVE_AMOUNT = 3;
	private final static int SQUARE_SIZE = 50;

	public static void main(String[] args) {
		new RectangleCollision();
	}

	private RectangleCollision() {
		super("Rectangle Collision");
		this.setPreferredSize(new Dimension(900, 500));
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
			this.setBackground(Color.WHITE);
			this.addMouseMotionListener(new MouseMotionListener() {
				
				@Override
				public void mouseMoved(MouseEvent e) {
					double targetX = e.getX();
					double targetY = e.getY();

					// TODO make it follow the mouse!!!
				}
				
				@Override
				public void mouseDragged(MouseEvent e) {
// nothing					
				}
			});
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(new Color(255,0,0,200));
			g.fillRect(userRect.x, userRect.y, userRect.width, userRect.height);

			g.setColor(Color.GRAY);
			for (int i = 0; i < boxArray.size(); i++) {
				g.setColor(boxArray.get(i).colour);
				g.fillRect((int) Math.round(boxArray.get(i).x),
						(int) Math.round(boxArray.get(i).y), SQUARE_SIZE,
						SQUARE_SIZE);
			}
		}

		private class BoxAI {

			private static final double MOVE_MULTIPLIER = 100;
			private static final double STEPS = 100;
			private static final double SPEED = 5.0;

			private double x;
			private double y;

			private Color colour;

			private BoxAI() {
				x = (Math.random() * RectangleCollision.this.getWidth());
				y = (Math.random() * RectangleCollision.this.getHeight());
				colour = new Color((int) (Math.random() * 255),
						(int) (Math.random() * 255),
						(int) (Math.random() * 255), 200);
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						while (true) {
							int deltaX = getDelta();
							int deltaY = getDelta();

							while (BoxAI.this.x + deltaX < 0
									|| BoxAI.this.x + deltaX > RectangleCollision.this
											.getWidth())
								deltaX = getDelta();
							while (BoxAI.this.y + deltaY < 0
									|| BoxAI.this.y + deltaY > RectangleCollision.this
											.getHeight())
								deltaY = getDelta();

							double xStep = deltaX / STEPS;
							double yStep = deltaY / STEPS;

							for (int step = 0; step < STEPS; step++) {
								BoxAI.this.x += xStep;
								BoxAI.this.y += yStep;

								try {
									Thread.sleep(Math.round(10 / SPEED));
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

			private int getDelta() {
				return (int) (Math.random() * MOVE_MULTIPLIER - (MOVE_MULTIPLIER / 2));
			}
		}
	}
}
