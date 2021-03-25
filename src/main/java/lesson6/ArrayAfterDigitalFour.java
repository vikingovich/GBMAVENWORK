package lesson6;

import java.util.Arrays;

public class ArrayAfterDigitalFour {

    public static void main(String[] args) {
        int[] array  = {1, 3, 4, 5, 8, 9, 10};
        ArrayAfterDigitalFour digitalFour = new ArrayAfterDigitalFour();


        try {

            System.out.println(Arrays.toString(digitalFour.convert(array)));
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (RuntimeException e ){
            e.printStackTrace();
        }


    }

    public  int[] convert(int[] array) throws RuntimeException{

        int count = 0;
        int c;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) count = i;
        }
        c = (array.length - count) - 1 ;
        int[] newarray = new int[c];
        if (count != 0) {

            int j = 0;
            for (int i = count + 1; i <array.length ; i++) {
                newarray[j] = array[i];
                j++;

            }
        }


        return newarray;

    }
}
