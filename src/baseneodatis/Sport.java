package baseneodatis;

import org.neodatis.odb.ODB;
/**
 *
 * @author Yasmin
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

    
}
