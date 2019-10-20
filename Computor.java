public class Computor {
    private float a;
    private float b;
    private float c;
    private int hpd;
    private String [] sumSplit;
    private float sol1;
    private float sol2;
    private boolean discriminant;

    public Computor (String sum){
        this.sumSplit =  sum.split(" ");
    }

    public void getCoefficients (){
        a = b = c = 0;
        for (int i = 0; i < sumSplit.length; i++){
            String temp = sumSplit[i];
            if (temp.contains("X^")){
                int tempCoef = Integer.parseInt(temp.replace("X^", ""));
                hpd = hpd < tempCoef ? tempCoef: hpd;
                if (hpd > 2 || tempCoef < 0)
                    break;
                else if (sumSplit[i - 1].contains("*") && i != 1){
                    Float t = Float.parseFloat(sumSplit[i - 2]);
                    t = (i > 2 && sumSplit[i - 3].contains("-")) ? t * (-1) : t;
                    t = (i > 3 && sumSplit[i - 4].contains("=")) ? t * (-1) : t;
                    t = (i > 2 && sumSplit[i - 3].contains("=")) ? t * (-1) : t;
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
        sol1 = sol2 = 0;
        if (a != 0 ){
            float sq = (b*b) - 4*a*c;
            System.out.println(sq);
            discriminant = (sq < 0) ? false : true;
            if (discriminant == true){
                sol1 = (-b + Sqrt(sq))/ (2 * a);
                sol2 = (-b - Sqrt(sq))/ (2 * a);
            }
        }else if (b != 0){
            sol1 = -1 * (c / b);
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
        if (a == 0 && b == 0 && c == 0 && sol1 == 0 && sol2 == 0)
            System.out.println("All real numbers");
        if (sol1 != 0 && discriminant == true)
            System.out.println(sol1);
        if (sol2 != 0 && discriminant == true)
            System.out.println(sol2);
        if (discriminant == false)
            System.out.println("The are no real solutions as the discriminant is negative");
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