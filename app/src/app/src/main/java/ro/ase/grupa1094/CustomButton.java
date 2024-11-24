package ro.ase.grupa1094;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;
import android.graphics.Color;
import androidx.core.content.ContextCompat;

public class CustomButton extends AppCompatButton {

    public CustomButton(Context context) {
        super(context);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.parseColor("#FFB6C1"));
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.rounded_button);
        setBackground(drawable);
        setTextColor(Color.WHITE);
        setPadding(16, 16, 16, 16);
    }
}
