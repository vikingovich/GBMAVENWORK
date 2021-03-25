package lesson6;

public class CheckDigitals {

    public static void main(String[] args) {
        int[] arr = {1, 4, 5, 3, 2};
        int[] arr2  = {2, 4, 5, 5};
        CheckDigitals checkDigitals = new CheckDigitals();

        System.out.println((checkDigitals.check(arr)));
        System.out.println((checkDigitals.check(arr2)));
    }

    public boolean check(int[] array){
        boolean one = false;
        boolean four = false;

        for (int i = 0; i < array.length; i++) {

            if (array[i] == 1) {
                one = true;

            }
            if (array[i] == 4){
                four = true;
            }

        }
        if (one == true & four == true) {return true;
        }else return false;
    }
}
