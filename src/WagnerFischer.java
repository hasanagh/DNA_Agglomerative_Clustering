import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;

public class WagnerFischer {

    public WagnerFischer() throws Exception {
    }

    public ArrayList<String> getSequencesFromXML(String fileName) throws Exception {

        Path path = Paths.get(fileName);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(Files.newInputStream(path));
        NodeList dnaList = doc.getElementsByTagName("DNA");
        Node DNA;
        Element DNAElt;
        ArrayList<String> sequences = new ArrayList<>();
        for (int i = 0; i < dnaList.getLength(); i++) {
            DNA = dnaList.item(i);
            if (DNA.getNodeType() == Node.ELEMENT_NODE) {
                DNAElt = (Element) DNA;
                sequences.add(DNAElt.getElementsByTagName("sequence").item(0).getTextContent());
            }

        }
        return sequences;
    }

    public double[][] getDistanceMatrix(ArrayList<String> sequences) {

        double[][] distanceMatrix = new double[sequences.size()][sequences.size()];

        for (int i = 0; i < sequences.size(); i++) {
            for (int j = 0; j < sequences.size(); j++) {
                distanceMatrix[i][j] = getEditDistance(sequences.get(i), sequences.get(j));
            }
        }
        return distanceMatrix;
    }

    public double[][] getSimilarityMatrix(double[][] dist) {
        double[][] simi = new double[dist.length][dist.length];
//        DecimalFormat fm = new DecimalFormat("0.#");
        String s1 = "";
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist.length; j++) {
//                s1 = fm.format(1 / (1 + dist[i][j]));
//                simi[i][j] = Double.parseDouble(s1);
                simi[i][j] = 1 / (1 + dist[i][j]);
            }
        }
        return simi;
    }


    public String toSt(double[][] arr) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                sb.append(arr[i][j] + "    |    ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public double getEditDistance(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        DecimalFormat fm = new DecimalFormat("0.#");
        String s1 = "";
        double[][] arr = new double[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++)
            arr[i][0] = i;
        for (int i = 1; i <= len2; i++)
            arr[0][i] = i;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                Nucleotide n1 = new Nucleotide(str1.charAt(i - 1));
                Nucleotide n2 = new Nucleotide(str2.charAt(j - 1));
                double m = n1.compare(n1, n2);
                arr[i][j] = Math.min(Math.min(arr[i - 1][j] + 1, arr[i][j - 1] + 1), arr[i - 1][j - 1] + m);
            }
        }
        s1 = fm.format(arr[len1][len2]);


        return Double.parseDouble(s1);
    }


    public Stack<ResultCluster> AgglomerativeClustering(ArrayList<Cluster> clusters, String typeOfLinkage, double stop) {
        Stack<ResultCluster> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        int cnt = 1;
        while (clusters.size() > 1) {

            double MINIMUM_SIMILARITY = 0;
            Cluster possible1 = new Cluster();
            Cluster possible2 = new Cluster();
            boolean entered = false;
            for (int i = 0; i < clusters.size(); i++) {

                Cluster cluster1 = clusters.get(i);
                for (int j = i + 1; j < clusters.size(); j++) {
                    double simi = 0.0;
                    Cluster cluster2 = clusters.get(j);

                    if (typeOfLinkage.equals("complete")) {
                        simi = cluster1.GetCompleteSimilarity(cluster2);
                    } else if (typeOfLinkage.equals("single")) {
                        simi = cluster1.GetSingleSimilarity(cluster2);
                    } else if (typeOfLinkage.equals("average")) {
                        simi = cluster1.GetAverageSimilarity(cluster2);
                    }
                    if (simi > MINIMUM_SIMILARITY && simi >= stop) {

                        MINIMUM_SIMILARITY = simi;
                        entered = true;
                        possible1 = cluster1;
                        possible2 = cluster2;
                    }

                }
            }
            if (entered) {
                int[] newIndexes = new int[possible1.getIndexes().length + possible2.getIndexes().length];
                for (int i = 0; i < possible1.getIndexes().length; i++) {
                    newIndexes[i] = possible1.getIndexes()[i];
                }
                for (int i = possible1.getIndexes().length; i < possible1.getIndexes().length + possible2.getIndexes().length; i++) {
                    newIndexes[i] = possible2.getIndexes()[i - possible1.getIndexes().length];
                }

                clusters.remove(possible1);
                clusters.remove(possible2);

                Cluster toAdd = new Cluster("parent", possible1.getSimilarities(), newIndexes);
                clusters.add(toAdd);
                double pgma = toAdd.generatePGMA();
                Cluster withPGMA = new Cluster("parent", newIndexes, pgma);

                sb.append("Cluster " + cnt + " consisting of " + possible1.toString() + " and " + possible2.toString() + " with pgma " + pgma + " and similarity " + MINIMUM_SIMILARITY + "\n");
                stack.push(new ResultCluster(withPGMA, MINIMUM_SIMILARITY, sb.toString(),possible1.getIndexes(),possible2.getIndexes(),newIndexes));
                cnt++;
            } else {
                break;
            }
        }
        return stack;
    }


}
