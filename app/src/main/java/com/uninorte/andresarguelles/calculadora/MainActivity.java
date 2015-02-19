package com.uninorte.andresarguelles.calculadora;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button_C;Button button_Equals;
    Button button_Sum;Button button_Subs;
    Button button_Multi;Button button_Div;
    Button button_Dot;

    TextView textView_Prev; TextView textView_Actual;

    ArrayList<Double> numArrayList;
    ArrayList<String> operArrayList;
    ArrayList<Boolean> operPriorArrayList;

    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);

        button_C = (Button) findViewById(R.id.button_C);
        button_Dot = (Button) findViewById(R.id.button_Dot);
        button_Sum = (Button) findViewById(R.id.button_Sum);
        button_Subs = (Button) findViewById(R.id.button_Subs);
        button_Multi = (Button) findViewById(R.id.button_Multi);
        button_Div = (Button) findViewById(R.id.button_Div);
        button_Equals = (Button) findViewById(R.id.button_Equals);

        textView_Prev = (TextView) findViewById(R.id.textView_Prev);
        textView_Actual = (TextView) findViewById(R.id.textView_Actual);

        //Listeners

        button0.setOnClickListener(clickNumberHandler);
        button1.setOnClickListener(clickNumberHandler);
        button2.setOnClickListener(clickNumberHandler);
        button3.setOnClickListener(clickNumberHandler);
        button4.setOnClickListener(clickNumberHandler);
        button5.setOnClickListener(clickNumberHandler);
        button6.setOnClickListener(clickNumberHandler);
        button7.setOnClickListener(clickNumberHandler);
        button8.setOnClickListener(clickNumberHandler);
        button9.setOnClickListener(clickNumberHandler);

        button_C.setOnClickListener(clickCHandler);
        button_Dot.setOnClickListener(clickDotHandler);
        //
        button_Sum.setOnClickListener(clickOperatorHandler);
        button_Subs.setOnClickListener(clickOperatorHandler);
        button_Multi.setOnClickListener(clickOperatorHandler);
        button_Div.setOnClickListener(clickOperatorHandler);
        button_Equals.setOnClickListener(clickEqualsHandler);

        //Initialization
        textView_Actual.setText("0");
        numArrayList = new ArrayList<Double>();
        operArrayList = new ArrayList<String>();
        operPriorArrayList = new ArrayList<Boolean>();
        index=0;
    }

    View.OnClickListener clickNumberHandler = new View.OnClickListener(){
        public void onClick(View v){
            Button bt = (Button) v;
            //Valido que no haya nada en el input, es decir, que haya solo un 0
            if (textView_Actual.getText().toString() == "0"){
                textView_Actual.setText("");
            }
            textView_Actual.setText(textView_Actual.getText().toString() + bt.getText());
        }
    };

    View.OnClickListener clickCHandler = new View.OnClickListener(){
        public void onClick (View v){
            v = (Button) v;
            String sentence = textView_Actual.getText().toString();
            //Si se va a borrar un operador, retroceder el index
            if ( (sentence.substring(sentence.length()-1).compareTo("+")==0)||(sentence.substring(sentence.length()-1).compareTo("-")==0)
                    || (sentence.substring(sentence.length()-1).compareTo("*")==0) || (sentence.substring(sentence.length()-1).compareTo("/")==0)){
                if(index > 0 ){
                    for (int i = sentence.length()-2; i >= 0; i--){
                        //Cuando encuentre otro operador más atras, actualiza el index
                        if ( (sentence.substring(i,i+1).compareTo("+")==0)||(sentence.substring(i,i+1).compareTo("-")==0)||
                                (sentence.substring(i,i+1).compareTo("*")==0)||(sentence.substring(i,i+1).compareTo("/")==0)){
                            index = i+1;
                            i=0;
                        }
                    }
                    //En caso que sea solo un numero en sentence, actauliza el index para q sea 0
                    if (index>sentence.length()-1){
                        index=0;
                    }
                }
            }
            //Luego de actualizar el index, se preocede a cambiar el texto en textView_Actual
            if(sentence.length() > 1){
                sentence = sentence.substring(0, sentence.length()-1);
                textView_Actual.setText(sentence);
            }else if (sentence.length() <= 1 ){
                textView_Actual.setText("0");
            }
            //textView_Prev.setText("Index = ".concat(""+index));
        }
    };

    View.OnLongClickListener longClickCHandler = new View.OnLongClickListener(){
        public boolean onLongClick(View v) {
            textView_Actual.setText(0);
            return true;
        }
    };

    View.OnClickListener clickDotHandler = new View.OnClickListener(){
        public void onClick (View v){
            v= (Button) v;
            /*if(textView_Actual.getText().toString().contains(".")== false){
                textView_Actual.setText(textView_Actual.getText().toString().concat("."));

            }*/
            String sentence = textView_Actual.getText().toString();
            //Si el substring de sentence desde la posicion index hasta el final (es decir, el último número escrito) tiene algun número, no se acepta otro punto
            if(! sentence.substring(index,sentence.length()).contains(".")){
                sentence= sentence.concat(".");
            }
            textView_Actual.setText(sentence);
        }
    };

    View.OnClickListener clickOperatorHandler = new View.OnClickListener(){
        public void onClick (View v){
            String sentence = textView_Actual.getText().toString();
            //Si el último caracter del string no es un operador...
            String lastChar = sentence.substring(sentence.length()-1);

            if (!((lastChar.compareTo("+")==0)||(lastChar.compareTo("-")==0) || (lastChar.compareTo("*")==0)|| (lastChar.compareTo("/")==0))){
                sentence = sentence.concat(((Button) v).getText().toString());
                index = sentence.length();
            }
            textView_Actual.setText(sentence);

        }
    };

    View.OnClickListener clickEqualsHandler = new View.OnClickListener(){
        public void onClick (View v){
            if(verifyIntegrityOnExpression(textView_Actual.getText().toString())){
                executeOperations();
            }else{
                textView_Prev.setText("ERROR");
            }
        }
    };

    //LLAMAR ESTA FUNCIÓn UNA VEZ SE PRESIONE EL BOTÓN EQUALS
    public boolean verifyIntegrityOnExpression(String sentence){
        numArrayList.clear();

        boolean isOk = true;
        //Chequear si el último caracter es un operador
        if ((sentence.substring(sentence.length()-1).compareTo("+")==0)||(sentence.substring(sentence.length()-1).compareTo("-")==0)
                ||(sentence.substring(sentence.length()-1).compareTo("*")==0)||(sentence.substring(sentence.length()-1).compareTo("/")==0)){
            isOk = false;
        }else {
            for (int i = 0; i < sentence.length() - 1; i++) {
                for (int j = 1; j < sentence.length(); j++) {
                    //Busco en sentence hasta que encuentre un operador y obtengo el numero antes del operador. No captura el último número
                    if ((sentence.substring(j, j + 1).compareTo("+") == 0) || (sentence.substring(j, j + 1).compareTo("-") == 0) ||
                            (sentence.substring(j, j + 1).compareTo("*") == 0) || (sentence.substring(j, j + 1).compareTo("/") == 0) ) {
                        String numS = sentence.substring(i, j);
                        Double num = Double.parseDouble(numS);
                        numArrayList.add(num);

                        String ope =sentence.substring(j,j+1);
                        operArrayList.add(ope);

                        if ( (ope.compareTo("*")==0) || (ope.compareTo("/")==0) ){
                            operPriorArrayList.add(true);
                        }else{
                            operPriorArrayList.add(false);
                        }

                        i=j+1;
                    }else if(j+1 == sentence.length()){
                        String numS = sentence.substring(i, j+1);
                        Double num = Double.parseDouble(numS);
                        numArrayList.add(num);

                        i=sentence.length();

                    }
                }
            }
        }
        return isOk;
    }

    public void executeOperations(){
        //Publico el string de la operación en textView_Prev
        textView_Prev.setText(textView_Actual.getText().toString());
        Double res = 0.0 ;

        if (numArrayList.size()>1){
            while (!operArrayList.isEmpty()){
                //Encuentra la primera operación de mayor prioridad
                int ind = operPriorArrayList.indexOf(true);


                //Si hay al menos una operación de alta prioridad
                if (ind != -1){
                    Double num1 = numArrayList.get(ind);
                    Double num2 = numArrayList.get(ind+1);
                    String operator = operArrayList.get(ind);

                    switch (operator){
                        case "*":   res = num1 * num2;
                                    break;
                        case "/":   res = num1 / num2;
                                    break;
                    }
                }else/*Si no hay operaciones de alta prioridad*/{
                    ind = operPriorArrayList.indexOf(false);
                    Double num1 = numArrayList.get(ind);
                    Double num2 = numArrayList.get(ind+1);
                    String operator = operArrayList.get(ind);

                    switch (operator){
                        case "+":   res = num1 + num2;
                            break;
                        case "-":   res = num1 - num2;
                            break;
                    }
                }
                numArrayList.set(ind,res); numArrayList.remove(ind+1);
                operArrayList.remove(ind);
                operPriorArrayList.remove(ind);

            }
            textView_Actual.setText(""+res);
        }
    }



    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
