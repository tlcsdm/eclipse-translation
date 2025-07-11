package com.tlcsdm.eclipse.translation.expressions;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.text.ITextSelection;

public class TextSelectionNotEmptyTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if ("hasTextSelection".equals(property) && receiver instanceof ITextSelection sel) {
			return sel.getLength() > 0;
		}
		return false;
	}
}
