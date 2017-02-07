package baseneodatis;

import java.util.Date;
import java.util.List;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 *
 * @author oracle
 */
public class Sport {

    private String name;
    static final String ODB_NAME = "neodatis";
    ODB odb = null;

    public Sport() {
    }

    public Sport(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void step1() throws Exception {
        try {
            // Create instance
            Sport sport1 = new Sport("volley-ball");
            Sport sport2 = new Sport("tennis");
            // Open the database
            odb = ODBFactory.open(ODB_NAME);
            // Store the object
            odb.store(sport1);
            odb.store(sport2);
        } finally {
            if (odb != null) {
                // Close the database
                odb.close();
            }
        }
    }

    public void step2() {

        Sport volleyball = new Sport("volley-ball");
        Player player1 = new Player("olivier", new Date(), volleyball, 1000);
        Player player2 = new Player("pierre", new Date(), volleyball, 1500);
        Player player3 = new Player("elohim", new Date(), volleyball, 2000);
        Player player4 = new Player("minh", new Date(), volleyball, 1300);
        Sport tennis = new Sport("tennis");
        Player player5 = new Player("luis", new Date(), tennis, 1600);
        Player player6 = new Player("carlos", new Date(), tennis, 2000);
        Player player7 = new Player("luis", new Date(), tennis, 1500);
        Player player8 = new Player("jose", new Date(), tennis, 3000);

        Team team1 = new Team("Paris");
        team1.addPlayer(player1);
        team1.addPlayer(player2);
        Team team2 = new Team("Montpellier");
        team2.addPlayer(player3);
        team2.addPlayer(player4);
        Team team3 = new Team("Bordeux");
        team3.addPlayer(player5);
        team3.addPlayer(player6);
        Team team4 = new Team("Lion");
        team4.addPlayer(player7);
        team4.addPlayer(player8);

        Game game1 = new Game(new Date(), volleyball, team1, team2);
        Game game2 = new Game(new Date(), tennis, team3, team4);

        try {
            // Open the database
            odb = ODBFactory.open(ODB_NAME);
            // Store the object
            odb.store(game1);
            odb.store(game2);
        } finally {
            if (odb != null) {
                // Close the database
                odb.close();
            }
        }
    }

    /**
     * Amosa o nome de todos os deportes, xogadores e fechas
     * @throws Exception
     */
    public void displaySports() throws Exception {
        // Open the database
        odb = ODBFactory.open(ODB_NAME);
        // Get all object of type Sport
        org.neodatis.odb.Objects<Sport> sports = odb.getObjects(Sport.class);
        // display each object
        //Sport sport = null;
        org.neodatis.odb.Objects<Team> teams = odb.getObjects(Team.class);
        org.neodatis.odb.Objects<Player> players = odb.getObjects(Player.class);
        org.neodatis.odb.Objects<Game> games = odb.getObjects(Game.class);

        while (sports.hasNext()) {
            Sport sport = sports.next();
            System.out.println("Sport: " + sport.getName());
        }

        while (teams.hasNext()) {
            Team team = teams.next();
            System.out.println("Team: " + team.getName());
        }

        while (players.hasNext()) {
            Player player = players.next();
            System.out.println("Player: " + player.getName());
        }

        while (games.hasNext()) {
            Game game = games.next();
            System.out.println(game.getWhen());
        }
        // Closes the database
        odb.close();
    }

    /**
     * Amosar o nome de todos os deportes
     */
    public void amosaDeportes() {
        odb = ODBFactory.open(ODB_NAME);
        IQuery select = new CriteriaQuery(Sport.class);

        org.neodatis.odb.Objects<Sport> sports = odb.getObjects(select);

        while (sports.hasNext()) {
            // Sport sport = sports.next();
            System.out.println("Sport: " + sports.next());
        }
        odb.close();
    }
    
    /**
     * Amosa o nome de xogador, nome do seu deporte 
     * favorito e o seu salario
     */
    public void consultaXogadores(){
        odb = ODBFactory.open(ODB_NAME);
        IQuery query = new CriteriaQuery(Sport.class);
        Sport volleyBall = (Sport) odb.getObjects(query).getFirst();
        Sport tennis = (Sport) odb.getObjects(query).getFirst();
        query = new CriteriaQuery(Player.class, Where.equal("favoriteSport", volleyBall));
        org.neodatis.odb.Objects<Player> players = odb.getObjects(query);

        int i =1;
        while(players.hasNext()){
            Player play = players.next();
           
            System.out.println((i++)+"." + play.getName() + " " + play.getFavoriteSport() + " " + play.getSalario());
        }
    }

    /**
     * Cambiar o nome dun xogador que teña por nome x
     */
    public void actualizaPorNomePlayer() {
        odb = ODBFactory.open(ODB_NAME);
        IQuery update = new CriteriaQuery(Player.class, Where.equal("name", "luis"));

        org.neodatis.odb.Objects<Player> players = odb.getObjects(update);
        Player xogadores = (Player) odb.getObjects(update).getFirst();

        xogadores.setName("pepito");

        odb.store(xogadores);
        odb.close();
    }

    /**
     * Amosar os nomes dos xogadores dun deporte
     */
    public void xogadoresDeporte() {

    }

}
