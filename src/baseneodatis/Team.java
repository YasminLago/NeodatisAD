package baseneodatis;

import java.util.ArrayList;

/**
 *
 * @author oracle
 */
public class Team {
    private String name;
    private ArrayList <Player> players = new ArrayList();
    
    public Team(){  
    }
    
    public Team(String name){
        this.name = name;
    }
    
    public void addPlayer(Player player){
        players.add(player);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
