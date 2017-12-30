package com.example.csaba.justjava;

import java.text.NumberFormat;
import java.util.Locale;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void increment (View view) {
        if (quantity > 99){
            Toast.makeText(this, "you can't order more than 100 coffes.", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity (quantity);
        displayMessage("");

    }
    public void decrement (View view) {
        if (quantity == 1){
            Toast.makeText(this, "you can't order less than 1 coffes.", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity (quantity);
        displayMessage("");
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        //get name from edittext
        EditText txtname = (EditText) findViewById(R.id.name);
        String name =  txtname.getText().toString();

        //checkbox checking for whipped cream
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //checkbox checking for choco
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChoco = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChoco);
        String priceMessage = createOrderSummery(name, price, hasWhippedCream, hasChoco);
        displayMessage(priceMessage);

        //intent makes email and send the order data
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "order from " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     * return price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean addChoco ) {
        int basePrice = 5;

        if (hasWhippedCream){
            basePrice = basePrice +1;
        }
        if (addChoco){
            basePrice = basePrice +2;
        }

        int price = quantity * basePrice;
        return price;
    }

    private String createOrderSummery (String name, int price, boolean hasWipped, boolean hasChoco) {
        String priceMessage = "name: " + name
                + "\nquantity: " + quantity
                + "\nadd whipped cream? " + hasWipped
                + "\nadd chocolate? " + hasChoco
                + "\ntotal $" + price
                + "\n " + getString(R.string.thankyou);  //this method gets the "thankyou" string from the Strings.xml
        return priceMessage;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given price on the screen.

    private void displayPrice(double number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText("total: " + NumberFormat.getCurrencyInstance(Locale.US).format(number));
    }
     */

    /**
     * This method displays the given text on the screen.
     */
     private void displayMessage(String message) {
        TextView orderSummeryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummeryTextView.setText(message);
    }

}








