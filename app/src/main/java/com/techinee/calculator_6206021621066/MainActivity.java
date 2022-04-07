package com.techinee.calculator_6206021621066;

import java.util.Stack;
import java.util.StringTokenizer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // ตรวจจับการคลิกปุ่มต่างๆ
    public void ButtonClickHandler(View v){
        EditText text1 = (EditText) findViewById(R.id.EditText01);


        switch(v.getId()){
            case R.id.ButtonNumber00:
                text1.append("0");
                break;
            case R.id.ButtonNumber01:
                text1.append("1");
                break;
            case R.id.ButtonNumber02:
                text1.append("2");
                break;
            case R.id.ButtonNumber03:
                text1.append("3");
                break;
            case R.id.ButtonNumber04:
                text1.append("4");
                break;
            case R.id.ButtonNumber05:
                text1.append("5");
                break;
            case R.id.ButtonNumber06:
                text1.append("6");
                break;
            case R.id.ButtonNumber07:
                text1.append("7");
                break;
            case R.id.ButtonNumber08:
                text1.append("8");
                break;
            case R.id.ButtonNumber09:
                text1.append("9");
                break;
            case R.id.ButtonSymbolDot:
                text1.append(".");
                break;
//            case R.id.ButtonSymbolBracketLeft:
//                text1.append("(");
//                break;
//            case R.id.ButtonSymbolBracketRight:
//                text1.append(")");
//                break;
            case R.id.ButtonSymbolDivide:
                text1.append("/");
                break;
            case R.id.ButtonSymbolMultiply:
                text1.append("*");
                break;
            case R.id.ButtonSymbolSubtract:
                text1.append("-");
                break;
            case R.id.ButtonSymbolAdd:
                text1.append("+");
                break;
            case R.id.ButtonSymbolDel:
                if(text1.getText().toString().length() > 0){
                    String strText1Tmp = text1.getText().toString().substring(0, text1.getText().toString().length()-1);

                    text1.setText("");
                    text1.append(strText1Tmp);
                }
                break;
            case R.id.ButtonSymbolClear:
                text1.setText("");
                text1.setText("");
                break;
            case R.id.ButtonSymbolExe:
                // เริ่มคำนวน
                String strStack = toPostfix(text1.getText().toString());
                float intValue = Calculator(strStack);

                // นำค่าที่คำนวนได้กลับไปใส่ใน text2
                text1.setText(Float.toString(intValue));
                break;
        }

    }

    // ตรวจสอบลำดับของเครื่องหมาย * / + -
    public static int getPriority(char chaOperator){
        if(chaOperator == '+' || chaOperator == '-'){
            return 1;
        }else if(chaOperator == '*' || chaOperator == '/'){
            return 2;
        }

        return 0;
    }

    // ตรวจสอบว่าเป็นตัวเลขหรือไม่
    public static boolean isFloat(String strInput){
        try{
            Float.parseFloat(strInput);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    // ทบทวนความรู้สมัยเรียนกันก่อน แปลงจาก infix เป็น postfix
    public static String toPostfix(String strInfix){
        String strExpression;
        String strPostfix = " ";

        strInfix  = strInfix.replaceAll("\\+|\\(|\\)|-|\\*|/", " $0 ");
        StringTokenizer strToken = new StringTokenizer(strInfix);

        Stack<Character> operatorStack = new Stack<Character>();

        while(strToken.hasMoreTokens()){
            strExpression = strToken.nextToken();

            if(Character.isDigit(strExpression.charAt(0))){
                strPostfix = strPostfix + " " + Float.parseFloat(strExpression);
            }else if(strExpression.equals("(")){
                Character operator = new Character('(');
                operatorStack.push(operator);
            }else if (strExpression.equals(")")){
                while(((Character) operatorStack.peek()).charValue() != '('){
                    strPostfix = strPostfix + " " + operatorStack.pop();
                }

                operatorStack.pop();
            }else{

                while(!operatorStack.isEmpty() && !(operatorStack.peek()).equals("(") && getPriority(strExpression.charAt(0)) <= getPriority(((Character) operatorStack.peek()).charValue())){
                    strPostfix = strPostfix + " " + operatorStack.pop();
                }

                Character operator = new Character(strExpression.charAt(0));
                operatorStack.push(operator);
            }
        }

        while(!operatorStack.isEmpty()){
            strPostfix = strPostfix + " " + operatorStack.pop();
        }

        return strPostfix;
    }

    // ทำการคำนวน  + - * / จาก postfix
    public static float Calculator(String strPostfix) {
        float a;
        float b;
        float result = 0;

        String[] arrPostfix = strPostfix.split(" ");
        Stack<Float> CalStack = new Stack<Float>();

        for(int i = 0; i < arrPostfix.length; i++){
            String ch = arrPostfix[i];

            if(isFloat(ch)){
                CalStack.push(Float.parseFloat(ch));
            }else{

                if(ch.equals("+")){
                    a = CalStack.pop();
                    b = CalStack.pop();
                    result = a + b;
                    CalStack.push(result);
                }else if(ch.equals("-")){
                    a = CalStack.pop();
                    b = CalStack.pop();
                    result = b - a;
                    CalStack.push(result);
                }else if(ch.equals("*")){
                    a = CalStack.pop();
                    b = CalStack.pop();
                    result = a * b;
                    CalStack.push(result);
                }else if(ch.equals("/")){
                    a = CalStack.pop();
                    b = CalStack.pop();
                    result = b / a;
                    CalStack.push(result);
                }
            }
        }

        return result;
    }
}