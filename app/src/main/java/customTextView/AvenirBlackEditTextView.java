package customTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by su on 27/4/17.
 */

public class AvenirBlackEditTextView extends EditText {
    public AvenirBlackEditTextView(Context context) {
        super(context);
        init();
    }

    public AvenirBlackEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AvenirBlackEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AvenirBlackEditTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {

        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "AvenirLTStd-Black.otf"));
    }
}
