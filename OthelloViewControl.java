
public class OthelloViewControl {

	private static InputStreamReader is = new InputStreamReader(System.in);
	private static BufferedReader br = new BufferedReader(is);

	OthelloModel om = new OthelloModel();

	OthelloViewControl() {
	}

	private String num = "１２３４５６７８";

	private void viewBoard() {
		System.out.println();
		System.out.println("　ＡＢＣＤＥＦＧＨ");
		for (int y = 0; y < OthelloModel.BOARD_SIZE; y++) {
			System.out.print(num.charAt(y));
			for (int x = 0; x < OthelloModel.BOARD_SIZE; x++) {
				if (om.getStone(y, x) == STONE.BLACK) {
					System.out.print("●");
				} else if (om.getStone(y, x) == STONE.WHITE) {
					System.out.print("〇");
				} else {
					System.out.print("・");
				}
			}
			System.out.println();
		}
	}

	STONE winner = STONE.NONE;
	int black = 0;
	int white = 0;

	private void judgeWinner() {
		for (int y = 0; y < OthelloModel.BOARD_SIZE; y++) {
			for (int x = 0; x < OthelloModel.BOARD_SIZE; x++) {
				if (om.getStone(y, x) == STONE.BLACK) {
					black++;
				}
				if (om.getStone(y, x) == STONE.WHITE) {
					white++;
				}
			}
		}
		if (black > white) {
			winner = STONE.BLACK;
		} else if (white > black) {
			winner = STONE.WHITE;
		}
	}

	public void startOthello() {
		STONE attacker;
		String str;
		char[] alpha = new char[2];
		boolean isGameEnd = false;
		while (isGameEnd == false) {
			viewBoard();
			isGameEnd = om.checkGameEnd();
			attacker = om.isAttacker();
			int xpos = -1;
			int ypos = -1;
			boolean isok = false;
			while (isok == false) {
				if (attacker == STONE.BLACK) {
					System.out.print("●の位置を入力してください：");
				} else if (attacker == STONE.WHITE) {
					System.out.print("○の位置を入力してください：");
				}
				try {
					str = br.readLine();
					str = str.trim();
				} catch (IOException e) {
					str = "";
					System.out.println("入力エラー！");
				}
				alpha[0] = str.charAt(0);
				alpha[1] = str.charAt(1);
				xpos = -1;
				ypos = -1;
				for (int i = 0; i < 2; i++) {
					if ('A' <= alpha[i] && alpha[i] <= 'Z') {
						xpos = alpha[i] - 'A';
					} else if ('a' <= alpha[i] && alpha[i] <= 'z') {
						xpos = alpha[i] - 'a';
					} else if ('1' <= alpha[i] && alpha[i] <= '8') {
						ypos = alpha[i] - '1';
					}
				}
				if (0 <= ypos && 0 <= xpos) {
					isok = om.isEnable(ypos, xpos);
				} else {
					System.out.println("入力エラー！");
				}
			}
			om.reverseStone(ypos, xpos);
		}
		judgeWinner();
		if (winner == STONE.BLACK) {
			System.out.println("●：" + black + "対〇：" + white + "で黒の勝です。");
		} else if (winner == STONE.WHITE) {
			System.out.println("●：" + black + "対〇：" + white + "で白の勝です。");
		} else {
			System.out.println("●：" + black + "対〇：" + white + "で引き分けです。");
		}
	}
}
