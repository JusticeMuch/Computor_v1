import javax.lang.model.util.ElementScanner6;

public class Computor {
    private float a;
    private float b;
    private float c;
    private int hpd;
    private String [] sumSplit;
    private String solution1;
    private String solution2;
    private boolean discriminant;

    public Computor (String sum){
        this.sumSplit =  sum.split(" ");
    }

    public void getCoefficients (){
        a = b = c = 0;
        int equateNeg = 1;
        for (int i = 0; i < sumSplit.length; i++){
            String temp = sumSplit[i];
            equateNeg = (temp.contains("=")) ? -1 : equateNeg;
            if (temp.contains("X^")){
                int tempCoef = Integer.parseInt(temp.replace("X^", ""));
                hpd = hpd < tempCoef ? tempCoef: hpd;
                if (hpd > 2 || tempCoef < 0)
                    break;
                else if (sumSplit[i - 1].contains("*") && i != 1){
                    Float t = Float.parseFloat(sumSplit[i - 2]);
                    t = (i > 2 && sumSplit[i - 3].contains("-")) ? t * (-1) : t;
                    t = t * equateNeg;
                    switch (tempCoef) {
                        case 0 :
                            c += t;
                            break;
                        case 1 :
                            b += t;
                            break;
                        case 2 :
                            a+= t;
                        default :
                    }
                }
            } 
        }
    }

    public void solve(){
        float real, imag;
        real = imag = 0;
        if (a != 0 ){
            float sq = (b*b) - 4*a*c;
            discriminant = (sq < 0) ? false : true;
            if (discriminant == true){
                solution1 = "X = " + String.valueOf((-b + Sqrt(sq))/ (2 * a));
                solution2 = "X = " + String.valueOf((-b - Sqrt(sq))/ (2 * a));
            }else {
                real  = -b/(2 * a);
                imag = Sqrt(sq * -1)/ (2 * a);
                solution1 = "X = " + String.valueOf(real) + " * X^0 +"+ String.valueOf(imag) + " * i";
                solution2 = "X = " + String.valueOf(real) + " * X^0 -"+ String.valueOf(imag) + " * i";
            }
        }else if (b != 0){
            solution1 = "X = " + String.valueOf(-1 * (c / b));
            solution2 = "";
        }
    }

    static float Sqrt(float x) 
    { 
        if (x == 0 || x == 1) 
            return x; 
  
        float i = 0.001f, result = 1; 
          
        while (result <= x) { 
            i += 0.001; 
            result = i * i; 
        } 
        return i;
    }

    public void printReducedForm (){
        
        String res = "Reduced form : ";
        res = (a != 0) ? (res + a + " * X^2 ") : res;
        res = (a != 0 && b != 0) ? (res + " + ") : res;
        res = (b != 0) ? (res + b + " * X^1 ") : res;
        res = (b != 0 && c != 0) ? (res + " + ") : res;
        res = (c != 0) ? (res + c + " * X^0 ") : res;
        System.out.println(res);
    }

    public void printSolutions(){
        System.out.println("The solutions are :");
        if (a == 0 && b == 0 && c == 0){
            System.out.println("All real numbers");
            System.exit(0);
        }else if (discriminant == true)
            System.out.println("Discriminant is positive");
        else if (a != 0 && b!= 0)
            System.out.println("Discriminant is negative");
        if (solution1.length() > 1)
            System.out.println(solution1);
        if (solution2.length() > 1 )
            System.out.println(solution2);
    }
    public static void main(String [] args){

        if (args.length != 1){
            System.out.println("No arguments or too many arguments given.");
            return ;
        }
        Computor comp = new Computor(args[0]);
        comp.getCoefficients();
        System.out.println("Polynomial degree : " + comp.hpd);
        if (comp.hpd < 0 || comp.hpd > 2){
            System.out.println("The polynomial degree is not within the range of 0 and 2");
            return ;
        }
        comp.solve();
        comp.printReducedForm();
        comp.printSolutions();
    }
}