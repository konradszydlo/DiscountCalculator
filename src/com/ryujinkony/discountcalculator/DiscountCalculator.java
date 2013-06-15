package com.ryujinkony.discountcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class DiscountCalculator extends Activity {

	private static final String PRICE_TOTAL = "PRICE_TOTAL";
	private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";

	private double currentPriceTotal; // price amount entered by the user
	private int currentCustomPercent; // discount % set with the SeekBar
	private EditText discount10EditText; // displays 10% discount
	private EditText total10EditText; // displays total with 10% discount
	private EditText discount15EditText; // displays 15% discount
	private EditText total15EditText; // displays total with 15% discount
	private EditText priceEditText; // accepts user input for price total
	private EditText discount20EditText; // displays 20% discount
	private EditText total20EditText; // displays total with 20% discount
	private TextView customDiscountTextView; // displays custom discount
												// percentage
	private EditText discountCustomEditText; // displays custom discount amount
	private EditText totalCustomEditText; // displays total with custom discount

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

		// check if app just started or is being restored from memory.
		if (icicle == null) {
			currentPriceTotal = 0.0;
			currentCustomPercent = 18;
		} else {
			currentPriceTotal = icicle.getDouble(PRICE_TOTAL);
			currentCustomPercent = icicle.getInt(CUSTOM_PERCENT);
		}

		discount10EditText = (EditText) findViewById(R.id.discount10EditText);
		total10EditText = (EditText) findViewById(R.id.total10EditText);
		discount15EditText = (EditText) findViewById(R.id.discount15EditText);
		total15EditText = (EditText) findViewById(R.id.total15EditText);
		discount20EditText = (EditText) findViewById(R.id.discount20EditText);
		total20EditText = (EditText) findViewById(R.id.total20EditText);
		customDiscountTextView = (TextView) findViewById(R.id.customDiscountTextView);

		// get the custom discount and total EditTexts
		discountCustomEditText = (EditText) findViewById(R.id.discountCustomEditText);
		totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);

		// get the billEditText
		priceEditText = (EditText) findViewById(R.id.priceEditText);

		// billEditTextWatcher handles billEditText's onTextChanged event
		priceEditText.addTextChangedListener(priceEditTextWatcher);

		// get the SeekBar used to set the custom tip amount
		SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
		customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
	}

	// updates 10, 15 and 20 percent discount EditTexts
	private void updateStandard() {
		// calculate price total with a ten percent discount
		double tenPercentDiscount = currentPriceTotal * .1;
		double tenPercentTotal = currentPriceTotal - tenPercentDiscount;

		// set discountTenEditText's text to tenPercentDiscount
		discount10EditText.setText(String.format("%.02f", tenPercentDiscount));
		// set totalTenEditText's text to tenPercentTotal
		total10EditText.setText(String.format("%.02f", tenPercentTotal));

		// calculate price total with a fifteen percent discount
		double fifteenPercentDiscount = currentPriceTotal * .15;
		double fifteenPercentTotal = currentPriceTotal - fifteenPercentDiscount;

		// set discountFifteenEditText's text to fifteenPercentDiscount
		discount15EditText.setText(String.format("%.02f",
				fifteenPercentDiscount));

		// set totalFifteenEditText's text to fifteenPercentTotal
		total15EditText.setText(String.format("%.02f", fifteenPercentTotal));
		// calculate price total with a twenty percent discount
		double twentyPercentDiscount = currentPriceTotal * .20;
		double twentyPercentTotal = currentPriceTotal - twentyPercentDiscount;

		// set discountTwentyEditText's text to twentyPercentDiscount
		discount20EditText.setText(String
				.format("%.02f", twentyPercentDiscount));

		// set totalTwentyEditText's text to twentyPercentTotal
		total20EditText.setText(String.format("%.02f", twentyPercentTotal));
	}

	// updates the custom discount and total EditTexts
	private void updateCustom() {
		// set customDiscountTextView's text to match the position of the
		// SeekBar
		customDiscountTextView.setText(currentCustomPercent + "%");

		// calculate the custom discount amount
		double customDiscountAmount = currentPriceTotal * currentCustomPercent
				* .01;

		// calculate the total price, including the custom discount
		double customTotalAmount = currentPriceTotal - customDiscountAmount;
		// display the discount and total price amounts
		discountCustomEditText.setText(String.format("%.02f",
				customDiscountAmount));
		totalCustomEditText.setText(String.format("%.02f", customTotalAmount));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.discount_calculator, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putDouble(PRICE_TOTAL, currentPriceTotal);
		outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
	}

	// called when the user changes the position of SeekBar
	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {
		// update currentCustomPercent, then call updateCustom
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// sets currentCustomPercent to position of the SeekBar's thumb
			currentCustomPercent = seekBar.getProgress();
			updateCustom(); // update EditTexts for custom discount and total
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	};

	// event-handling object that responds to priceEditText's events
	private TextWatcher priceEditTextWatcher = new TextWatcher() {
		// called when the user enters a number
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// convert priceEditText's text to a double
			try {
				currentPriceTotal = Double.parseDouble(s.toString());
			} catch (NumberFormatException e) {
				currentPriceTotal = 0.0; // default if an exception occurs
			}
			// update the standard and custom discount EditTexts
			updateStandard(); // update the 10, 15 and 20% EditTexts
			updateCustom(); // update the custom discount EditTexts
		}

		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
	};
}
