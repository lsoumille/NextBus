package data;

import org.junit.Test;
import utils.Position;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 13/01/16.
 */
public class ManagerHorairesTest {

    ManagerHoraires mh = new ManagerHoraires();
    int line2 = 2;
    int line12 = 12;
    int line33 = 33;
    String existingBusStop1 = "ANTIBES";
    Position busStop1Pos = new Position(10,10);
    String nonExistingBusStop = "PARIS";


    @Test
    public void testGetBusStop() {
        List<String> busStops2 = mh.getBusStop(line2);
        List<String> busStops12 = mh.getBusStop(line12);

        assertTrue(busStops2.contains(existingBusStop1));
        assertFalse(busStops2.contains(nonExistingBusStop));


        assertTrue(busStops12.contains(existingBusStop1));

        assertEquals(mh.getBusStop(line33),null);
    }

    @Test
    public void testGetBusStopWithPos() {
        Map<String, Position> busStopsWithPos2 = mh.getBusStopWithPos(line2);
        Map<String,Position> busStopsWithPos12 = mh.getBusStopWithPos(line12);

        assertTrue(busStopsWithPos2.containsKey(existingBusStop1));
        assertEquals(busStopsWithPos2.get(existingBusStop1).getLatitude(), busStop1Pos.getLatitude());
        assertEquals(busStopsWithPos2.get(existingBusStop1).getLongitude(), busStop1Pos.getLongitude());
        assertFalse(busStopsWithPos2.containsKey(nonExistingBusStop));

        assertTrue(busStopsWithPos12.containsKey(existingBusStop1));
    }

    @Test
    public void testGetAllBusStopWithPos() {
        Map<String, Position> allBusStopsWithPos = mh.getAllBusStopsWithPos();
        assertTrue(allBusStopsWithPos.containsKey(existingBusStop1));
        assertEquals(allBusStopsWithPos.get(existingBusStop1).getLatitude(), busStop1Pos.getLatitude());
        assertEquals(allBusStopsWithPos.get(existingBusStop1).getLongitude(), busStop1Pos.getLongitude());
        assertFalse(allBusStopsWithPos.containsKey(nonExistingBusStop));
    }

    @Test
    public void testIsBusStop() {
        assertTrue(mh.isBusStop(existingBusStop1));
        assertFalse(mh.isBusStop(nonExistingBusStop));
    }

    @Test
    public void testGetHours() {
        String[] hours2 = mh.getHours(line2, existingBusStop1);
        String[] hours12 = mh.getHours(line12, existingBusStop1);

        assertEquals(hours2[3], "6:45");
        assertEquals(hours12[4], "12:02");
    }

    @Test
    public void testGetNearestStop() {
        assertEquals(mh.getNearestStop(busStop1Pos), existingBusStop1);
    }

    @Test
    public void testGetPositionForStop() {
        assertEquals(mh.getPositionForStop(existingBusStop1).getLatitude(), busStop1Pos.getLatitude());
        assertEquals(mh.getPositionForStop(existingBusStop1).getLongitude(), busStop1Pos.getLongitude());
    }


}
