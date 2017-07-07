/**
 * Created by Hasan on 4/22/2017.
 */
public class Cluster {
    private double[][] similarities;
    private int[] indexes;
    private String type;
    private double PGMA;


    public Cluster() {
    }

    public Cluster(String type, double[][] similarities, int indexes[]) {
        this.similarities = similarities;
        this.indexes = indexes;
        this.type = type;
    }

    public Cluster(String type, int[] indexes, double PGMA) {
        this.indexes = indexes;
        this.type = type;
        this.PGMA = PGMA;
    }


    public double getPGMA() {
        return PGMA;
    }

    public void setPGMA(double PGMA) {
        this.PGMA = PGMA;
    }

    public int[] getIndexes() {
        return indexes;

    }

    public void setIndexes(int[] indexes) {
        this.indexes = indexes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[][] getSimilarities() {
        return similarities;
    }

    public void setSimilarities(double[][] similarities) {
        this.similarities = similarities;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indexes.length; i++) {
            sb.append(indexes[i] + " ");
        }
        return sb.toString();
    }


    public double GetAverageSimilarity(Cluster cluster2) {
        double nbOfSimilarities = 0;
        double totalSimilarity = 0;
        for (int i = 0; i < indexes.length; i++) {
            for (int j = 0; j < cluster2.getIndexes().length; j++) {

                nbOfSimilarities++;
                totalSimilarity = totalSimilarity + similarities[indexes[i] - 1][cluster2.getIndexes()[j] - 1];
            }
        }
        return totalSimilarity / nbOfSimilarities;
    }


    public double GetCompleteSimilarity(Cluster cluster2) {
        double MAXSIMI = 1.0;
        for (int i = 0; i < indexes.length; i++) {
            for (int j = 0; j < cluster2.getIndexes().length; j++) {
                if (similarities[indexes[i] - 1][cluster2.getIndexes()[j] - 1] < MAXSIMI) {
                    MAXSIMI = similarities[indexes[i] - 1][cluster2.getIndexes()[j] - 1];
                }
            }
        }
        return MAXSIMI;
    }


    public double GetSingleSimilarity(Cluster cluster2) {
        double MINSIMI = 0.0;
        for (int i = 0; i < indexes.length; i++) {
            for (int j = 0; j < cluster2.getIndexes().length; j++) {
                if (similarities[indexes[i] - 1][cluster2.getIndexes()[j] - 1] > MINSIMI) {
                    MINSIMI = similarities[indexes[i] - 1][cluster2.getIndexes()[j] - 1];
                }
            }
        }
        return MINSIMI;
    }


    public double generatePGMA() {
        double sum = 0.0;
        double cnt = 0.0;
        for (int i = 0; i < indexes.length; i++) {
            for (int j = i + 1; j < indexes.length; j++) {
                sum = sum + similarities[indexes[i] - 1][indexes[j] - 1];
                cnt++;
            }
        }

        return sum / cnt;

    }


}
