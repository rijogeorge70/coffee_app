package com.example.user.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {


    int quantity=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        if(number>1)
        quantityTextView.setText("" + number + " coffees");
        else
            quantityTextView.setText("" + number + " coffee");
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increament(View view) {
        quantity++;
        if (quantity>100) {
            quantity = 100;
            Toast.makeText(this, "Sorry, You cannot have less than 100 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        display(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        quantity--;
        if (quantity<1) {
             quantity = 1;
            Toast.makeText(this,"Sorry, You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        display(quantity);


    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText yourname = (EditText) findViewById(R.id.name);
        String name = yourname.getText().toString();
        CheckBox whippedcream = (CheckBox) findViewById(R.id.whipped_cream);
        boolean whipped = whippedcream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.choco);
        boolean chocola = chocolate.isChecked();
        int price = calculatePrice(whipped,chocola);
        String priceMessage = createOrderSummary(price,whipped,chocola,name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order Summary for" + name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);
    }

    private int calculatePrice(boolean Checked,boolean cho){
        if (Checked==true&&cho==true){
           return quantity*8;
        }
        else if (Checked==true&&cho==false){
            return quantity*6;
        }
        else if (Checked==false&&cho==true){
            return quantity*7;
        }
        else{
        return quantity*5;
        }
    }

    private String createOrderSummary(int price,boolean Checked,boolean cho,String nam){
        String priceMessage = "Name :"+ nam;
        priceMessage+="\nAdd Whipped cream "+ Checked +"?";
        priceMessage+="\nAdd Chocolate "+ cho+"?";
        priceMessage+="\nQuantity : " +quantity;
        priceMessage+="\nTotal : $"+price;
        priceMessage+="\nThank you!";
        return priceMessage;
    }

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }
}