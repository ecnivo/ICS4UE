package unit4_exercise18_multithreading;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RectangleCollision extends JFrame {

	private final static int SQUARE_SIZE = 35;
	private static double moveAmount;

	protected double targetX;
	protected double targetY;

	private boolean[] pressed = new boolean[7];

	protected ContentPanel contentPanel = new ContentPanel();

	public static void main(String[] args) {
		new RectangleCollision();
	}

	private RectangleCollision() {
		super("Rectangle Collision");
		this.setPreferredSize(new Dimension(900, 500));
		this.setLocation(100, 100);
		this.setFocusable(true);
		this.add(contentPanel);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// nothing
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN)
					pressed[2] = false;
				else if (e.getKeyCode() == KeyEvent.VK_UP)
					pressed[3] = false;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					pressed[4] = false;
				else if (e.getKeyCode() == KeyEvent.VK_LEFT)
					pressed[5] = false;
				else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					pressed[6] = false;
				}

				checkKeys();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_O) {
					contentPanel.addBox();
				} else if (e.getKeyCode() == KeyEvent.VK_A) {
					contentPanel.addBox(true);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN)
					pressed[2] = true;
				else if (e.getKeyCode() == KeyEvent.VK_UP)
					pressed[3] = true;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					pressed[4] = true;
				else if (e.getKeyCode() == KeyEvent.VK_LEFT)
					pressed[5] = true;
				else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					pressed[6] = true;
				}

				checkKeys();
			}
		});

		this.pack();
	}

	private synchronized void checkKeys() {
		if (pressed[2]
				&& targetY + moveAmount + SQUARE_SIZE < RectangleCollision.this
						.getHeight())
			targetY += moveAmount;
		if (pressed[3] && targetY - moveAmount > 0)
			targetY -= moveAmount;
		if (pressed[4]
				&& targetX + moveAmount + SQUARE_SIZE < RectangleCollision.this
						.getWidth())
			targetX += moveAmount;
		if (pressed[5] && targetX - moveAmount > 0)
			targetX -= moveAmount;
		if (pressed[6])
			moveAmount = 15;
		else
			moveAmount = 3;
	}

	private class ContentPanel extends JPanel {

		private Box userRect;
		private ArrayList<BoxAI> boxArray = new ArrayList<BoxAI>();
		protected double maxTargetDistance;

		private ContentPanel() {
			userRect = new Box();
			updateMaxDistance();
			Thread userThread = new Thread(new Runnable() {

				@Override
				public void run() {
					byte collisionCheckCounter = 0;
					while (true) {
						double deltaY = targetY - userRect.y;
						double deltaX = targetX - userRect.x;

						double stepY = deltaY / 10;
						double stepX = deltaX / 10;

						userRect.x += stepX;
						userRect.y += stepY;
						try {
							Thread.sleep(Math.round(10 / Box.MOVE_SPEED));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						collisionCheckCounter++;

						if (collisionCheckCounter > 30) {
							if (userRect.isColliding(userRect.x, userRect.y)
									|| userRect.isColliding(userRect.x
											+ SQUARE_SIZE, userRect.y)
									|| userRect.isColliding(userRect.x
											+ SQUARE_SIZE, userRect.y
											+ SQUARE_SIZE)
									|| userRect.isColliding(userRect.x,
											userRect.y + SQUARE_SIZE)) {
								ContentPanel.this.addBox();
							}
							collisionCheckCounter = 0;
							checkKeys();
						}
					}
				}
			});
			userThread.start();
			boxArray.add(new BoxAI());
			this.setBackground(Color.WHITE);

			this.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseMoved(MouseEvent e) {
					targetX = e.getX();
					targetY = e.getY();
				}

				@Override
				public void mouseDragged(MouseEvent e) {
					// nothing
				}
			});

			this.addComponentListener(new ComponentListener() {

				@Override
				public void componentShown(ComponentEvent e) {
					// nothing
				}

				@Override
				public void componentResized(ComponentEvent e) {
					updateMaxDistance();
					targetX = RectangleCollision.this.getWidth() / 2;
					targetY = RectangleCollision.this.getHeight() / 2;
				}

				@Override
				public void componentMoved(ComponentEvent e) {
					// nothing
				}

				@Override
				public void componentHidden(ComponentEvent e) {
					// nothing
				}
			});
		}

		private void updateMaxDistance() {
			maxTargetDistance = Math.sqrt(RectangleCollision.this.getWidth()
					* RectangleCollision.this.getHeight()) / 4;
		}

		private void addBox() {
			addBox(false);
		}

		private void addBox(boolean exponent) {
			if (!exponent)
				boxArray.add(new BoxAI());
			else {
				for (int i = boxArray.size(); i > 0; i--) {
					boxArray.add(new BoxAI());
				}
			}
		}

		public synchronized void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(new Color(255, 0, 0, 200));
			g.fillRect((int) (Math.round(userRect.x)),
					(int) (Math.round(userRect.y)), SQUARE_SIZE, SQUARE_SIZE);

			for (int i = 0; i < boxArray.size(); i++) {
				g.setColor(boxArray.get(i).colour);
				g.fillRect((int) Math.round(boxArray.get(i).x),
						(int) Math.round(boxArray.get(i).y), SQUARE_SIZE,
						SQUARE_SIZE);
			}
		}

		private class Box {

			protected static final double STEPS = 150;
			protected static final double MOVE_SPEED = 4.5;

			protected double x;
			protected double y;

			protected Color colour;

			private Box() {
				x = (Math.random() * RectangleCollision.this.getWidth());
				y = (Math.random() * RectangleCollision.this.getHeight());
				colour = new Color((int) (Math.random() * 255),
						(int) (Math.random() * 255),
						(int) (Math.random() * 255), 200);
			}

			protected int getDelta() {
				return (int) (Math.random() * maxTargetDistance - (maxTargetDistance / 2));
			}

			protected boolean isColliding(double x, double y) {
				for (int i = boxArray.size() - 1; i >= 0; i--) {
					double targetLeftX = boxArray.get(i).x;
					double targetRightX = boxArray.get(i).x + SQUARE_SIZE;
					double targetTopY = boxArray.get(i).y;
					double targetBottomY = boxArray.get(i).y + SQUARE_SIZE;
					if (x <= targetRightX && x >= targetLeftX
							&& y <= targetBottomY && y >= targetTopY)
						return true;
				}
				return false;
			}
		}

		private class BoxAI extends Box {

			private BoxAI() {
				super();

				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						while (true) {

							if (BoxAI.this.x + SQUARE_SIZE > RectangleCollision.this
									.getWidth()
									|| BoxAI.this.x < 0
									|| BoxAI.this.y + SQUARE_SIZE > RectangleCollision.this
											.getHeight() || BoxAI.this.y < 0) {
								BoxAI.this.x = RectangleCollision.this
										.getWidth() / 2;
								BoxAI.this.y = RectangleCollision.this
										.getHeight() / 2;
							}

							int deltaX = getDelta();
							int deltaY = getDelta();

							while (BoxAI.this.x + deltaX < 0
									|| BoxAI.this.x + deltaX + SQUARE_SIZE > RectangleCollision.this
											.getWidth())
								deltaX = getDelta();
							while (BoxAI.this.y + deltaY < 0
									|| BoxAI.this.y + deltaY + SQUARE_SIZE > RectangleCollision.this
											.getHeight())
								deltaY = getDelta();

							double xStep = deltaX / STEPS;
							double yStep = deltaY / STEPS;

							for (int step = 0; step < STEPS; step++) {
								BoxAI.this.x += xStep;
								BoxAI.this.y += yStep;

								try {
									Thread.sleep(Math.round(10 / MOVE_SPEED));
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
