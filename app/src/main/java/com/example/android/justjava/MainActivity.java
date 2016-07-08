package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity{

    int quantity = 2;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameView = (EditText)findViewById(R.id.name);
        String name = nameView.getText().toString();

        CheckBox whippedCreame = (CheckBox) findViewById(R.id.checkbox_whippedCream);
        boolean hasWhippedCream = whippedCreame.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.checkbox_Chocolate);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasChocolate, hasWhippedCream);

        displayPrice(price);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        //displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject) + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);

        }


    }

    /**
     * Calculates the price of the order.
     * @return total price
     */
    private int calculatePrice(boolean addChocolate, boolean addWhippedCream) {
        int basePrice = 5;

        if(addChocolate){
            basePrice = basePrice + 2;
        }

        if(addWhippedCream){
            basePrice = basePrice + 1;
        }

        return quantity * basePrice;

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    public void increment(View view){

        if (quantity == 100) {
            Toast toast = Toast.makeText(MainActivity.this, getString(R.string.toomanyCoffee), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    public void decrement(View view){
        quantity = quantity - 1;

        if (quantity == 1){
            Toast toast = Toast.makeText(MainActivity.this, getString(R.string.noCoffee) , Toast.LENGTH_SHORT);
            toast.show();
           return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }


    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * Creates a summary of the order
     * @param price
     * @param addWhippedCream
     * @return
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name){
        String priceMessage = getString(R.string.order_summary_name) + name;
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream) + addWhippedCream;
        priceMessage += "\n" + getString(R.string.order_summary_chocolate) + addChocolate;
        priceMessage += "\n" + getString(R.string.order_summary_quantity) + quantity;
        priceMessage += "\n" + getString(R.string.order_summary_price) + price;
        priceMessage += " \n" + getString(R.string.thank_you);
        return priceMessage;

    }




}
