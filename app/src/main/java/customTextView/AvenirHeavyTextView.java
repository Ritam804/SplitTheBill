package customTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by su on 27/4/17.
 */

public class AvenirHeavyTextView extends TextView {
    public AvenirHeavyTextView(Context context) {
        super(context);
        init();
    }

    public AvenirHeavyTextView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AvenirHeavyTextView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AvenirHeavyTextView(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {

        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "AvenirLTStd-Heavy.otf"));
    }
}
