package com.junehouse.testAlgorithm;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public ArrayList<String> solution(int number, String[] str) {
        ArrayList<String> result = new ArrayList<>();
        for (String data: str) {
            char[] chars = data.toCharArray();
            int lt = 0; // 0번째 위치
            int rt = chars.length - 1; // 마지막 텍스트 위치

            while(lt < rt) {
                char temp = chars[lt];
                chars[lt] = chars[rt];
                chars[rt] = temp;
                lt++;
                rt--;
            }

            String temp2 = String.valueOf(chars);
            result.add(temp2);
        }
        return result;
    }
    public static void main(String[] args) {
        Main T = new Main();
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        String[] str = new String[n];

        for (int i = 0; i < n; i++) {
            str[i] = sc.next();
        }

        for(String result : T.solution(n, str)) {
            System.out.println(result);
        }
    }
}
