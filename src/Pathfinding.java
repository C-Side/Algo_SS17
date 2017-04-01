import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Lukas, Daniel und Pawel on 22.03.2017.
 */
public class Pathfinding {


    int x = 0;
    int y = 0;
    int oldX = 0;
    int oldY = 0;
    int n = 0;
    long possiblePaths = 0;
    boolean rechtsUnten = false;
    boolean linksOben = false;


    private void calculatePaths() {

        while (noch möglicher Weg vorhanden) {
            if (weg noch nicht gegangen) {
                findPath(x, y, oldX, oldY);
            }
        }
        System.out.println("Anzahl möglicher Wege: " + possiblePaths);
    }

    private void findPath(int x, int y, int oldX, int oldY) {

        if (x == n && y == 0) {
            x = 0;
            y = 0;
            possiblePaths++;
            speicherePfad();
        }

        //schritt nach links oben
        else if (x <= n && y <= n && !rechtsUnten && x > 0) {
            oldX = x;
            oldY = y;
            x--;
            y++;
            linksOben = true;
            rechtsUnten = false;
            findPath(x, y, oldX, oldY);
        }

        //schritt nach rechts unten
        else if (x <= n && y <= n && !linksOben && y > 0) {
            oldX = x;
            oldY = y;
            x++;
            y--;
            rechtsUnten = true;
            linksOben = false;
            findPath(x, y, oldX, oldY);
        }

        //schritt nach oben
        else if (y >= x && y < n) {
            oldX = x;
            oldY = y;
            y++;
            linksOben = false;
            rechtsUnten = false;
            findPath(x, y, oldX, oldY);
        }

        //schritt nach rechts
        else if (x >= y && x <= n) {
            oldX = x;
            oldY = y;
            x++;
            linksOben = false;
            rechtsUnten = false;
            findPath(x, y, oldX, oldY);
        }

        //schritt nach rechts oben
        else if (x <= n && y < n) {
            oldX = x;
            oldY = y;
            x++;
            y++;
            linksOben = false;
            rechtsUnten = false;
            findPath(x, y, oldX, oldY);
        }

        //falls totes Ende, speichere schritt und gehe einen Schritt zurück
        else {
            speichereSchritt();
            geheZurück();
        }
    }

    public void setN(int n) {
        this.n = n;
    }

    public static void main(String args[]) {
        Pathfinding pathfinding = new Pathfinding();

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Geben sie  n  ein: ");
        try {
            pathfinding.setN(Integer.parseInt(input.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pathfinding.calculatePaths();
    }
}
