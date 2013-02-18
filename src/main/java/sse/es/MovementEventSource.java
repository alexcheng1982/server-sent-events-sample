package sse.es;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

import java.util.logging.*;

public class MovementEventSource implements EventSource {
	
	private int width = 800;
	private int height = 600;
	private int stepMax = 5;
	private int x = 0;
	private int y = 0;
	private Random random = new Random();
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public MovementEventSource(int width, int height, int stepMax) {
		this.width = width;
		this.height = height;
		this.stepMax = stepMax;
		this.x = random.nextInt(width);
		this.y = random.nextInt(height);
	}

	@Override
	public void onOpen(Emitter emitter) throws IOException {
		query(emitter);
	}

	@Override
	public void onResume(Emitter emitter, String lastEventId)
			throws IOException {
		updatePosition(lastEventId);
		query(emitter);
	}
	
	private void updatePosition(String id) {
		if (id != null) {
			String[] pos = id.split(",");
			if (pos.length > 1) {
				int xPos = -1, yPos = -1;
				try {
					xPos = Integer.parseInt(pos[0], 10);
					yPos = Integer.parseInt(pos[1], 10);
				} catch (NumberFormatException e) {
					
				}
				if (isValidMove(xPos, yPos)) {
					x = xPos;
					y = yPos;
				}
			}
		}
	}
	
	private void query(Emitter emitter) throws IOException {
		emitter.comment("Start sending movement information.");
		emitter.comment(padding());
		while(true) {
			emitter.comment("");
			move();
			String id = String.format("%s,%s", x, y);
			emitter.id(id);
			emitter.data(id);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				logger.log(Level.WARNING, "Movement query thread interrupted. Close the connection.", e);
				break;
			}
		}
		emitter.close();
	}

	@Override
	public void onClose() {
		
	}
	
	private String padding() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= 2048; i++) {
			builder.append(" ");
		}
		return builder.toString();
	}
	
	private void move() {
		while (true) {
			int[] move = getMove();
			int xNext = x + move[0];
			int yNext = y + move[1];
			if (isValidMove(xNext, yNext)) {
				x = xNext;
				y = yNext;
				break;
			}
		}
	}

	private boolean isValidMove(int x, int y) {
		return x >= 0 && x <= width && y >=0 && y <= height;
	}
	
	private int[] getMove() {
		int[] xDir = new int[] {-1, 0, 1, 0};
		int[] yDir = new int[] {0, -1, 0, 1};
		int dir = random.nextInt(4);
		return new int[] {xDir[dir] * random.nextInt(stepMax), yDir[dir] * random.nextInt(stepMax)};
	}
}
