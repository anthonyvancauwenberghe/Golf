
import java.util.ArrayList;

/**
 * Created by nibbla on 14.03.16.
 */
public class MainMenue {
    ArrayList<Course> playlist = new ArrayList<>();
    ArrayList<Player> players;
    GUI gui;

    static void main(String[] args){

    }

    void loadPlaylist(String path){
        String contend = Utils.readFile(path);
        String[] p = contend.split("\n");
        playlist.clear();
        for (int i = 0; i < p.length; i++) {
            playlist.add(Course.loadCourse(p[i]));
        }
    }

    void savePlayList(String path){
        String content = "";
        for (int i = 0; i < playlist.size(); i++) {
            content+=playlist.get(i).getName()+".txt\n";
        }
        Utils.saveFile(path,content);
    }
    void emptyPlaylist(){
        this.playlist.clear();
    }

    void addPlayer(Player p){
        players.add(p);
    }
    void removePlayers(){
        players.clear();
    }
    void startRound(){

    }

    void designCourse(String path){

    }
    void savePlaylist(String path){


    }

}
