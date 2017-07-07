/**
 * Created by Hasan on 4/25/2017.
 */
public class ResultCluster {

    private Cluster cluster;
    private double similarity;
    private String string;
    private int[] indexes1, indexes2, newIndexes;


    public int[] getNewIndexes() {
        return newIndexes;
    }

    public void setNewIndexes(int[] newIndexes) {
        this.newIndexes = newIndexes;
    }

    public ResultCluster(Cluster cluster, double similarity, String string, int[] indexes1, int[] indexes2, int[] newIndexes) {
        this.cluster = cluster;
        this.similarity = similarity;
        this.string = string;
        this.indexes1 = indexes1;
        this.indexes2 = indexes2;
        this.newIndexes = newIndexes ;

    }

    public int[] getIndexes1() {
        return indexes1;
    }

    public void setIndexes1(int[] indexes1) {
        this.indexes1 = indexes1;
    }

    public int[] getIndexes2() {
        return indexes2;
    }

    public void setIndexes2(int[] indexes2) {
        this.indexes2 = indexes2;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
