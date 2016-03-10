package com.grexoft.resume.widgets;

import com.grexoft.resume.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.widget.TextView;


public class TextViewWithFont extends TextView {
	private final static int DEFAULT_DIMENSION = 0;
	private final static int TYPE_BOLD = 1;
	private final static int TYPE_ITALIC = 2;
	private static final int TYPE_NORMAL = 0;
	// private final static int TYPE_REGULAR = 3;
	private final static int FONT_CALIBRI = 1;
	// private final static int FONT_OPEN_SANS = 2;

	private final static int FONT_OSWALD = 2;

	private int fontType;
	private int fontName;
	private float spacing = Spacing.NORMAL;
	private CharSequence originalText;

	public TextViewWithFont(Context context) {
		super(context);
		init(null, 0);
	}

	public TextViewWithFont(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public TextViewWithFont(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	public float getSpacing() {
		return this.spacing;
	}

	public void setSpacing(float spacing) {
		this.spacing = spacing;
		applySpacing();
	}

	@Override
	public void setText(CharSequence text, BufferType type) {

		originalText = text;
		applySpacing();

	}

	@Override
	public CharSequence getText() {
		return originalText;
	}

	private void applySpacing() {
		if (this == null || this.originalText == null)
			return;

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < originalText.length(); i++) {
			builder.append(originalText.charAt(i));
			if (i + 1 < originalText.length()) {
				builder.append("\u00A0");
			}
		}
		SpannableString finalText = new SpannableString(builder.toString());
		if (builder.toString().length() > 1) {
			for (int i = 1; i < builder.toString().length(); i += 2) {
				finalText.setSpan(new ScaleXSpan((spacing + 1) / 10), i, i + 1,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}

		super.setText(finalText, BufferType.SPANNABLE);
	}

	private void init(AttributeSet attrs, int defStyle) {
		// Load attributes

		if (!isInEditMode()) {

			final TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.font, defStyle, 0);

			fontName = a.getInt(R.styleable.font_name, DEFAULT_DIMENSION);
			fontType = a.getInt(R.styleable.font_type, DEFAULT_DIMENSION);
			spacing = a.getFloat(R.styleable.font_spacing, 0);
			a.recycle();
			Typeface font = null;
			if (fontName == FONT_CALIBRI) {

				font = Typeface.createFromAsset(getContext().getAssets(),
						"fonts/CALIBRIL.TTF");

			}

			else if (fontName == FONT_OSWALD) {
				font = Typeface.createFromAsset(getContext().getAssets(),
						"fonts/Oswald-Regular.ttf");
			}

			setFontType(font, fontType);

			applySpacing();
		}

	}

	private void setFontType(Typeface font, int fontType) {
		if (fontType == TYPE_BOLD) {
			setTypeface(font, Typeface.BOLD);
		} else if (fontType == TYPE_ITALIC) {
			setTypeface(font, Typeface.ITALIC);
		} else {
			setTypeface(font);
		}

		// applySpacing();
	}

	public class Spacing {
		public final static float NORMAL = 1;
	}
}