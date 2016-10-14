package utils;

/**
 * Created by user on 11/01/16.
 */
public class Position {
    private int longitude;
    private int latitude;

    public Position() {
        this(0,0);
    }

    public Position(int longitude, int latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (longitude != position.longitude) return false;
        return latitude == position.latitude;

    }

    @Override
    public int hashCode() {
        int result = longitude;
        result = 31 * result + latitude;
        return result;
    }
}
