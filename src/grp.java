import java.util.ArrayList;

/**
 * Created by Hasan on 4/28/2017.
 */
public class grp {

    private double startX,endX,midpoint,ynow;
    private ArrayList<Integer> indexes ;


    public ArrayList<Integer> getIndexes() {
        return indexes;
    }

    public void setIndexes(ArrayList<Integer> indexes) {
        this.indexes = indexes;
    }

    public grp(double startX, double endX, double ynow, double midpoint , ArrayList<Integer> indexes) {
        this.startX = startX;
        this.endX = endX;
        this.midpoint = midpoint;
        this.ynow = ynow ;
        this.indexes = indexes ;


    }
    public grp joinGroups(grp g , double ynows){

        ArrayList<Integer> indexexBoth = indexes;
        int s = indexexBoth.size();
        int sd = g.getIndexes().size();
        for(int i=s; i< s+sd ; i++){
            indexexBoth.add(g.getIndexes().get(i-s));
        }


        return new grp(midpoint,g.getMidpoint(),ynows,(midpoint+g.getMidpoint())/2,indexexBoth);

    }

    public double getStartX() {

        return startX;
    }
    public double getYnow() {

        return ynow;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }
    public void setYnow(double startX) {
        this.ynow = ynow;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getMidpoint() {
        return midpoint;
    }

    public void setMidpoint(double midpoint) {
        this.midpoint = midpoint;
    }
}
