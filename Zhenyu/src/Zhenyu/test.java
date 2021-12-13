package Zhenyu;
import java.util.*;

// @author JINGWEN ZHANG

public class test {

    
    private static final double[] LetterFrequencyTable = {
    		8.167, 1.492, 2.782, 4.253, 12.702,
            2.228, 2.015, 6.094, 6.966, 0.153,
            0.772, 4.025, 2.406, 6.749, 7.507,
            1.929, 0.095, 5.987, 6.327, 9.056,
            2.758, 0.978, 2.360, 0.150, 1.974,
            0.074};
   
    // Calculate coincidences base on duplicateAndMove each displacement
    private static void duplicateAndMove(char[] Ciphertext) {
        for (int j = 0; j < Ciphertext.length; j++) {
            int coincidenceTimes = 0;
            for (int i = 0; i < Ciphertext.length; i++) {
                if (Ciphertext[i] == Ciphertext[(i + j) % Ciphertext.length]) {
                    coincidenceTimes++;
                }
            }
            System.out.println("The " + j + " displacement " + coincidenceTimes + " coincidences");
        }
    }


	private static Scanner extracted() {
		return new Scanner( System.in );
	}

   // use vigener table for decipher 
    private static String decryptWithKey(String Ciphertext, String key) {
        char[] CiphertextArray = Ciphertext.toCharArray();
        char[] keyArray = key.toCharArray();
        StringBuilder plaintext = new StringBuilder(314);
        for (int i = 0; i < CiphertextArray.length; i++) {
            char originChar = (char) (
                    (((CiphertextArray[i] - 65) - (keyArray[i % key.length()] - 65)) + 26) % 26 + 65);
            plaintext.append(originChar);
            //System.out.println(CiphertextArray[i]+ "------"+keyArray[i]);
        }
        return plaintext.toString();
    }

 
    private static char findKey(Map<Character, Integer> count) {
        double maxProb = Double.MIN_VALUE;
        int bestI = 0;
        for (int i = 0; i < 26; i++) {
            double currentProb = 0;
           
            for (int j = 0; j < 26; j++) {
                currentProb += LetterFrequencyTable[j] * count.get((char) (65 + (j + i) % 26));
            }
            if (currentProb > maxProb) {
                maxProb = currentProb;
                bestI = i;
            }
        }
        return (char) (bestI + 65);
    }

   
    private static Map<Character, Integer> countAnalysis(char[] charArr) {
        Map<Character, Integer> map = new LinkedHashMap<>();
        
        for (int i = 65; i <= 90; i++) {
            char target = (char) i;
            int coincidenceTimes = 0;
            for (char ch : charArr) {
                if (ch == target) {
                    coincidenceTimes++;
                }
            }
            map.put(target, coincidenceTimes);
        }
        return map;
    }

   
    private static List<char[]> divideIntoGroups(char[] charArray, int keyLength) {
        ArrayList<char[]> list = new ArrayList<>();
        for (int i = 0; i < keyLength; i++) {
            int j = i;
            StringBuilder sb = new StringBuilder();
            while (j < charArray.length) {
                sb.append(charArray[j]);
                //j的跳跃宽度是密钥的长度
                j += keyLength;
            }
            list.add(sb.toString().toCharArray());
        }
        return list;
    }

    public static void main(String[] args) {
    	System.out.print( "Enter your vigener cipher text: " ); 
    	String Ciphertext= extracted().next( ).toUpperCase();
        StringBuilder key = new StringBuilder();
        char[] charArray = Ciphertext.toCharArray();
   
        duplicateAndMove(charArray);

  
        
        System.out.print("Please input the most likely keyLength based on the tips above: ");
        int keyLength;
        try {
            keyLength = extracted().nextInt();
        } catch (InputMismatchException e) {
            throw new RuntimeException("you must enter an Int");
        }
        
    
        List<char[]> groups = divideIntoGroups(charArray, keyLength);

       
        for (char[] group : groups) {
            
            Map<Character, Integer> count = countAnalysis(group);
        
            char aKey = findKey(count);
            key.append(aKey);
        }
        System.out.println("The key may be：" + key.toString());
        System.out.println(decryptWithKey(Ciphertext, key.toString()));
        
    }
}
