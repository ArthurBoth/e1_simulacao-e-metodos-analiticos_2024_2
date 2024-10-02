package auxiliaries.fileData;

public class FileDataWrapper {
    public String content;
    public int arrivalsIndex;
    public int queuesIndex;
    public int networksIndex;
    public int rndnumbersIndex;
    public int numbersPerSeedIndex;
    public int seedsIndex;

    public FileDataWrapper(
                String content, int arrivalsIndex, int queuesIndex, int networksIndex,
                int rndnumbersIndex, int numbersPerSeedIndex, int seedsIndex) {
        this.content             = content;
        this.arrivalsIndex       = arrivalsIndex;
        this.queuesIndex         = queuesIndex;
        this.networksIndex       = networksIndex;
        this.rndnumbersIndex     = rndnumbersIndex;
        this.numbersPerSeedIndex = networksIndex;
        this.seedsIndex          = seedsIndex;
    }
}
