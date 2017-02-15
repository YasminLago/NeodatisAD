package baseneodatis;

import static baseneodatis.Sport.ODB_NAME;
import java.util.Date;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.core.query.nq.SimpleNativeQuery;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 *
 * @author Yasmin
 */
public class Consultas {

    ODB odb = null;

    /**
     * Crea dous obxetos tipo Sport
     *
     * @throws Exception
     */
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

    /**
     * Crea dous obxetos tipo Sport, Players, Teams y Games
     */
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
     *
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
     * ****************************CONSULTAS*********************************
     */
    /**
     * Amosar o nome de todos os deportes(Funciona)
     */
    public void amosaDeportes() {
        odb = ODBFactory.open(ODB_NAME);
        IQuery select = odb.criteriaQuery(Sport.class);
        org.neodatis.odb.Objects<Sport> sports = odb.getObjects(select);
        int i = 1;
        while (sports.hasNext()) {
            Sport sport = sports.next();
            System.out.println("Sport " + (i++) + ": " + sport.getName());
        }
        odb.close();
    }

    /**
     * Amosa o nome de xogador, nome do seu deporte favorito e o seu salario (Funciona)
     */
    public void consultaXogadores() {
        odb = ODBFactory.open(ODB_NAME);     
        IQuery query = odb.criteriaQuery(Player.class);
        org.neodatis.odb.Objects<Player> players = odb.getObjects(query);
        int i = 1;
        while (players.hasNext()) {
            Player player = players.next();
            System.out.println("Player " + (i++) + ": " + player.getName() + " " 
                    + player.getFavoriteSport().getName() + " " 
                    + player.getSalario());
        }
        odb.close();
    }

    /**
     * Cambiar o nome dun xogador que teña por nome x (Funciona)
     */
    public void actualizaPorNomePlayer(String nomeAntigo, String nomeNovo) {
        odb = ODBFactory.open(ODB_NAME);
        IQuery update = odb.criteriaQuery(Player.class, Where.equal("name", nomeAntigo));
        org.neodatis.odb.Objects<Player> players = odb.getObjects(update);
        while (players.hasNext()) {
            Player play = players.next();
            play.setName(nomeNovo);
            odb.store(play);
        }
        odb.close();
    }

    /**
     * Amosar os nomes dos xogadores dun deporte(Funciona)
     */
    public void xogadoresDeporte(String deporte) {
        odb = ODBFactory.open(ODB_NAME);
        IQuery query = odb.criteriaQuery(Player.class, Where.equal("favoriteSport.name", deporte));
        org.neodatis.odb.Objects<Player> player = odb.getObjects(query);
        System.out.println("Sport: " + deporte);
        int i = 1;
        while (player.hasNext()) {
            Player play = player.next();
            System.out.println("Player " + (i++) + ": " + play.getName());
        }
        odb.close();
    }

    /**
     * Amosa xogador e equipo dos xogadores que cobren menos da cantidade
     * indicada (Funciona)
     * @param cantidade
     */
    public void devoltarEquiposConXogadoresMenosDunhaCantidade(int cantidade) {
        odb = ODBFactory.open(ODB_NAME);
        IQuery query1 = odb.criteriaQuery(Player.class, Where.lt("salario", cantidade));
        org.neodatis.odb.Objects<Player> player = odb.getObjects(query1);
        int i = 1;
        int z = 1;
        while (player.hasNext()) {
            Player play = player.next();
            System.out.println("Player " + (i++) + ": " + play.getName());
            IQuery query2 = odb.criteriaQuery(Team.class, Where.contain("players", play));
            org.neodatis.odb.Objects<Team> teams = odb.getObjects(query2);
            
            while(teams.hasNext()){
                Team team = teams.next();
                System.out.println("Team Player " + (z++) + ": " + team.getName());
            }
        }
        odb.close();
    }
    
    /**
     * Cambia os nomes de todos os xogadores do deporte especificado que a partires 
     * deste momento pasaran todos a chamarse co nomeNovo (Funciona)
     * @param deport
     * @param nomeNovo 
     */
    public void igualaNomesPorDeporte(String deport, String nomeNovo){
          odb = ODBFactory.open(ODB_NAME);
        IQuery update = odb.criteriaQuery(Sport.class, Where.equal("name", deport));
        Sport sport = (Sport) odb.getObjects(update).getFirst();
        System.out.println("Sport: " + sport.getName());
        update = odb.criteriaQuery(Player.class,Where.equal("favoriteSport", sport)); 
        org.neodatis.odb.Objects<Player> players = odb.getObjects(update);
        while (players.hasNext()) {
            Player play = players.next();
            play.setName(nomeNovo);
            odb.store(play);
            System.out.println(play.getName());
        }
        odb.close();
    }
    
    /**
     * Dada as primeiras letras dun deporte amosa os nomes dos xogadores que 
     * o practican (Funciona)
     * @param dep 
     */
    public void nativeQueryXogadoresDeporte(final String dep){
        odb = ODBFactory.open(ODB_NAME);
        IQuery query = new SimpleNativeQuery(){
          public boolean match(Player player){
              return player.getFavoriteSport().getName().toLowerCase().startsWith(dep);
          }  
        };
        org.neodatis.odb.Objects<Player> players = odb.getObjects(query);
        System.out.println("Players que xogan a Volley-ball:");
        int i = 1;
        while(players.hasNext()){
            Player player = players.next();
            System.out.println((i++) + ". " + player.getName());
        }

        odb.close();
    }
    
    /**
     * Dado un nome elimina todos os xogadores que teñan dito nome (Funciona)
     * @param nome 
     */
    public void borraXogadoresPorNome(String nome){
        odb = ODBFactory.open(ODB_NAME);
        IQuery delete = odb.criteriaQuery(Player.class, Where.equal("name", nome));
        org.neodatis.odb.Objects<Player> players = odb.getObjects(delete);
        while (players.hasNext()) {
            Player play = players.next();
            odb.delete(play);
        }
        odb.close();
    }
    
    /**
     * Dado un deporte e un nome de xogador lista os xogadores de dito nome que 
     * practiquen dito deporte (Funciona)
     * @param nome
     * @param depor 
     */
    public void listarXogadoresPorNomeEDeporte(String nome, String depor){
        odb = ODBFactory.open(ODB_NAME);
        IQuery query = odb.criteriaQuery(Sport.class,Where.equal("name",depor));
        Sport sport = (Sport) odb.getObjects(query).getFirst();
        System.out.println("Sport: " + sport.getName());
        query = odb.criteriaQuery(Player.class,Where.and().
                add(Where.equal("favoriteSport", sport)).
                add(Where.equal("name",nome)));        
        org.neodatis.odb.Objects<Player> players = odb.getObjects(query);
        int i = 1;
        while (players.hasNext()) {
            Player player = players.next();
            System.out.println("Player " + (i++) + ": " + player.getName());
        }
        odb.close();  
    }
    
    /**
     * Dado o nome dun xogador e do equipo ao que pertence, aumentar nunha
     * cantidade o salario de  todos os xogadores de dito equipo que teñan dito 
     * nome de xogador
     * @param xog
     * @param equi
     * @param aumento 
     */
    public void aumentaSalarioXogadoresEquipo(String xog, String equi, int aumento){
        odb = ODBFactory.open(ODB_NAME);
        IQuery query = odb.criteriaQuery(Player.class, Where.equal("name", xog));
        org.neodatis.odb.Objects<Player> player = odb.getObjects(query);
        query = odb.criteriaQuery(Team.class,Where.and().
                    add(Where.equal("players", player)).
                    add(Where.equal("name", equi)));

        int i = 1;
        while (player.hasNext()) {
            Player play = player.next();
            System.out.println("Player " + (i++) + ": " + play.getName());
            
                play.setSalario(aumento);
                odb.store(play);
                System.out.println("Player: " + play.getName() + " " + play.getSalario());
            }
        
        odb.close();  
    }
}