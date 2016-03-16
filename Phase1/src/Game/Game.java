package Game;

import java.util.ArrayList;

public class Game {
    ArrayList<Player> players;
    int currentPlayer;
    Course c;

    Long time;
    private boolean gameFinished = false;

    public boolean isGameFinished() {
        return gameFinished;
    }



    public Game(Course course, ArrayList<Player> players) {
        this.players = players;
        currentPlayer = 0;
        time = 0L;
    }


    public static void main(String[] args) {
        System.out.println("golf game: whazzaa");
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public Course getCourse() {
        return c;
    }

    public Long getTime() {
        return time;
    }
}