public class Nucleotide {
    private char base;
    private char[] poss;

    public Nucleotide(char base) {
        this.base = base;

        if (base == 'A') {
            poss = new char[1];
            poss[0] = 'A';
        }
        if (base == 'G') {
            poss = new char[1];
            poss[0] = 'G';
        }
        if (base == 'C') {
            poss = new char[1];
            poss[0] = 'C';
        }
        if (base == 'T') {
            poss = new char[1];
            poss[0] = 'T';
        }
        if (base == 'R') {
            poss = new char[2];
            poss[0] = 'G';
            poss[1] = 'A';
        }
        if (base == 'Y') {
            poss = new char[2];
            poss[0] = 'T';
            poss[1] = 'C';
        }
        if (base == 'K') {
            poss = new char[2];
            poss[0] = 'G';
            poss[1] = 'T';
        }
        if (base == 'M') {
            poss = new char[2];
            poss[0] = 'A';
            poss[1] = 'C';
        }
        if (base == 'S') {
            poss = new char[2];
            poss[0] = 'G';
            poss[1] = 'C';
        }
        if (base == 'W') {
            poss = new char[2];
            poss[0] = 'A';
            poss[1] = 'T';
        }
        if (base == 'B') {
            poss = new char[3];
            poss[0] = 'G';
            poss[1] = 'T';
            poss[2] = 'C';
        }
        if (base == 'D') {
            poss = new char[3];
            poss[0] = 'G';
            poss[1] = 'A';
            poss[2] = 'T';
        }
        if (base == 'H') {
            poss = new char[3];
            poss[0] = 'A';
            poss[1] = 'C';
            poss[2] = 'T';
        }
        if (base == 'V') {
            poss = new char[3];
            poss[0] = 'G';
            poss[1] = 'C';
            poss[2] = 'A';
        }
        if (base == 'N') {
            poss = new char[4];
            poss[0] = 'A';
            poss[1] = 'G';
            poss[2] = 'C';
            poss[3] = 'T';
        }
    }

    public char getBase() {
        return base;
    }

    public void setBase(char base) {
        this.base = base;
    }

    public char[] getPoss() {
        return poss;
    }

    public void setPoss(char[] poss) {
        this.poss = poss;
    }

    public double compare(Nucleotide n1, Nucleotide n2) {
        double count = 0;
        int size = n1.poss.length * n2.poss.length;
        if (n1.base == n2.base) {
            count = 0;
        } else {
            for (int i = 0; i < n1.poss.length; i++)
                for (int j = 0; j < n2.poss.length; j++) {
                    if (!(n1.poss[i] + "").equals(n2.poss[j] + ""))
                        count++;
                }
        }
        return count / size;
    }

}
