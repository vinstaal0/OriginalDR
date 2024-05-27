package nl.vinstaal0.Dungeonrealms.Monstermechanics;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Spawner {

    private final Location loc;

    private final Player creator;

    public ArrayList<SpawnerData> spawnerData = new ArrayList<>();

    private SpawnerData spawnerDataMidCreation;

    private int spawnInterval = 30;
    private int lowerRange = 1;
    private int upperRange = 5;

    private int levelRange = 4;

    private String status;

    /**
     * Created a spwner object using
     * @param loc the location of the spawner
     * @param creator the creator of the spawner
     */
    public Spawner(Location loc, Player creator) {
        this.loc = loc;
        this.creator = creator;
    }

    /**
     * Returns the spawner info to String in the old Minecade format
     * @return String
     */
    @Deprecated
    public String toString() {


        StringBuilder sb = new StringBuilder();
        sb.append(loc.getX());
        sb.append(",");
        sb.append(loc.getY());
        sb.append(",");
        sb.append(loc.getZ());
        sb.append("=@");

        for (SpawnerData data : spawnerData) {
            sb.append(data.toString());
        }

        sb.append("@");
        sb.append(spawnInterval);

        sb.append("#");
        sb.append(lowerRange);
        sb.append("-");
        sb.append(upperRange);

        sb.append("$");
        sb.append(levelRange);

        sb.append("%");

        return sb.toString();
    }


    /**
     * Add the spawner data that is being modified
     * @param spawnerData the spawner data that is being modified while creating the specific spawner
     */
    public void addSpawnerData(SpawnerData spawnerData) {
        this.spawnerDataMidCreation = spawnerData;
    }

    /**
     * Retrieves the spawner data that is being modified
     * @return SpawnerData the spawner data that is being modified
     */
    public SpawnerData getSpawnerDataMidCreation() {
        return spawnerDataMidCreation;
    }

    /**
     * Finishes the spawner that was being modified to either finish the spawner or add more spawner-data
     */
    public void finishSpawnerData() {
        this.spawnerData.add(spawnerDataMidCreation);
        spawnerDataMidCreation = null;
    }

    public void setSpawnInterval(int interval) {
        this.spawnInterval = interval;
    }

    public void setLowerRange(int lowerRange) {
        this.lowerRange = lowerRange;
    }

    public void setUpperRange(int upperRange) {
        this.upperRange = upperRange;
    }

    public void setLevelRange(int levelRange) {
        this.levelRange = levelRange;
    }

    public Location getLoc() {
        return loc;
    }

    public Player getCreator() {
        return creator;
    }

    /**
     * return: custom_name, range,  interval or level
     * */
    public String getStatus() {
        return this.status;
    }

    public int getSpawnInterval() {
        return this.spawnInterval;
    }

    public int getLowerRange() {
        return this.lowerRange;
    }

    public int getUpperRange() {
        return this.upperRange;
    }

    public int getLevelRange() {
        return this.levelRange;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
