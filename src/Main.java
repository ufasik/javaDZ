public class Main {
    public static void main(String[] args) {
        GameResultWriter gameResultWriter = new GameResultWriter();
        BullsAndCowsGame game = new BullsAndCowsGame(gameResultWriter);
        game.play();
    }
}