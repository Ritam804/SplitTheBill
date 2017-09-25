package customTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by su on 27/4/17.
 */

public class AvenirBookTextView extends TextView {
    public AvenirBookTextView(Context context) {
        super(context);
    }

    public AvenirBookTextView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public AvenirBookTextView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AvenirBookTextView(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init() {

        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "AvenirLTStd-Book.otf"));
    }
}
