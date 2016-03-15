import java.util.ArrayList;

public class Game {
    ArrayList<Player> players;
    int currentPlayer;
    Course c;
    ArrayList<Ball> balls;
    int currentBall;
    Long time;
    private boolean gameFinished = false;

    public boolean isGameFinished() {
        return gameFinished;
    }



    public Game(Course course, ArrayList<Player> players) {
        this.players = players;
        currentPlayer = 0;
        balls = new ArrayList<>(players.size());
        for (int i = 0; i < players.size(); i++) {
            balls.add(new Ball());
        }
        currentBall = 0;
        time = 0L;
    }

    public void shoot(double angX, double angY, double power){
        
        //TODO how the physics gets implemented.
        time++; //basic idea of this method. needs to be adjusted.
        balls.get(currentBall).magic(c, angX, angY, power);
        players.get(currentPlayer).addStroke();

        //checks if the ball is still in Play and switches players according to that.
        if (!balls.get(currentBall).inPlay()){ //ball is in the whole
            players.get(currentPlayer).resetCurrentStrokes();
            balls.remove(currentBall);
            currentPlayer = (currentPlayer) % players.size();
            currentBall = (currentBall) % balls.size();

            if (balls.size()==0)gameFinished=true;
        }else {
            currentPlayer = (currentPlayer + 1) % players.size();
            currentBall = (currentBall + 1) % balls.size();
        }
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

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public Ball getCurrentBall() {
        return balls.get(currentBall);
    }

    public Long getTime() {
        return time;
    }
}