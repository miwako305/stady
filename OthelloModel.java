
enum STONE {
	NONE, WHITE, BLACK,
}

public class OthelloModel {

	public static final int BOARD_SIZE = 8;
	private static final int MAX_DIRECTION = 8;
	private STONE attacker = STONE.BLACK;
	private STONE[][] board = new STONE[OthelloModel.BOARD_SIZE][OthelloModel.BOARD_SIZE];

	OthelloModel() {
		initial_game();
	}

	private void initial_game() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = STONE.NONE;
			}
		}
		board[BOARD_SIZE / 2][BOARD_SIZE / 2] = STONE.WHITE;
		board[BOARD_SIZE / 2 - 1][BOARD_SIZE / 2 - 1] = STONE.WHITE;
		board[BOARD_SIZE / 2 - 1][BOARD_SIZE / 2] = STONE.BLACK;
		board[BOARD_SIZE / 2][BOARD_SIZE / 2 - 1] = STONE.BLACK;
		attacker = STONE.BLACK;
	}

	public STONE getStone(int ypos, int xpos) {
		STONE stone = STONE.NONE;
		if (0 <= ypos && ypos < BOARD_SIZE && 0 <= xpos && xpos < BOARD_SIZE) {
			stone = board[ypos][xpos];
		}
		return stone;
	}

	public STONE isAttacker() {
		return attacker;
	}

	public boolean checkGameEnd() {
		boolean end = false;
		boolean skip = true;
		Loop1: for (int y = 0; y < BOARD_SIZE; y++) {
			for (int x = 0; x < BOARD_SIZE; x++) {
				if (board[y][x] == STONE.NONE) {
					boolean enable = isEnable(x, y);
					if (enable == true) {
						skip = false;
						break Loop1;
					}
				}
			}
		}
		if (skip == true) {
			if (attacker == STONE.BLACK) {
				attacker = STONE.WHITE;
			} else {
				attacker = STONE.BLACK;
			}
			end = true;
			Loop2: for (int y = 0; y < BOARD_SIZE; y++) {
				for (int x = 0; x < BOARD_SIZE; x++) {
					if (board[y][x] == STONE.NONE) {
						boolean enable = isEnable(x, y);
						if (enable == true) {
							end = false;
							break Loop2;
						}
					}
				}
			}
		}
		return end;
	}

	int ypich[] = { -1, -1, 0, 1, 1, 1, 0, -1 };
	int xpich[] = { 0, 1, 1, 1, 0, -1, -1, -1 };

	private boolean checkReverseOne(int ypos, int xpos, int direction) {
		boolean reverse = false;
		int y = 0;
		int x = 0;
		int count = 0;
		for (int next = 1; next < BOARD_SIZE; next++) {
			y = ypos + ypich[direction] * next;
			x = xpos + xpich[direction] * next;
			if (y < 0 || BOARD_SIZE <= y || x < 0 || BOARD_SIZE <= x) {
				count = 0;
				break;
			}
			if (board[y][x] == attacker) {
				break;
			} else if (board[y][x] == STONE.NONE) {
				count = 0;
				break;
			}
			count++;
		}
		if (0 < count) {
			reverse = true;
		}
		return reverse;
	}

	private boolean checkReverseAll(int ypos, int xpos) {
		boolean reverse = false;
		for (int direction = 0; direction < MAX_DIRECTION; direction++) {
			reverse = checkReverseOne(ypos, xpos, direction);
			if (reverse == true) {
				break;
			}
		}
		return reverse;
	}

	public boolean isEnable(int ypos, int xpos) {
		boolean enable = false;
		if (0 <= ypos && ypos < BOARD_SIZE && 0 <= xpos && xpos < BOARD_SIZE) {
			if (board[ypos][xpos] == STONE.NONE) {
				enable = checkReverseAll(ypos, xpos);
			}
		}
		return enable;
	}

	private void reverseStoneOne(int ypos, int xpos, int direction) {
		int y, x;
		for (int next = 1; next < BOARD_SIZE; next++) {
			y = ypos + ypich[direction] * next;
			x = xpos + xpich[direction] * next;
			if (y < 0 || BOARD_SIZE <= y || x < 0 || BOARD_SIZE <= x) {
				break;
			}
			if (board[y][x] == attacker) {
				break;
			} else if (board[y][x] == STONE.NONE) {
				break;
			}
			board[y][x] = attacker;
		}
	}

	public void reverseStone(int ypos, int xpos) {
		boolean reverse = false;
		if (0 <= ypos && ypos < BOARD_SIZE && 0 <= xpos && xpos < BOARD_SIZE) {
			for (int direction = 0; direction < MAX_DIRECTION; direction++) {
				if (checkReverseOne(ypos, xpos, direction) == true) {
					reverseStoneOne(ypos, xpos, direction);
					reverse = true;
				}
			}
			if (reverse == true ) {
				board[ypos][xpos] = attacker;
				if (attacker == STONE.BLACK) {
					attacker = STONE.WHITE;
				} else {
					attacker = STONE.BLACK;
				}
			}
		}
	}

	public void setStoneForTest(int ypos, int xpos, STONE stone) {
		if (0 <= ypos && ypos < BOARD_SIZE && 0 <= xpos && xpos < BOARD_SIZE) {
			board[ypos][xpos] = stone;
		}
	}

	public void setAttackerForTest(STONE stone) {
		attacker = stone;
	}
}
