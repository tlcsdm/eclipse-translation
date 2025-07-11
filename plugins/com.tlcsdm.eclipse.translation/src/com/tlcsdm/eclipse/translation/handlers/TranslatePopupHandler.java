package com.tlcsdm.eclipse.translation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class TranslatePopupHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ITextSelection selection = getSelection(event);
		if (selection != null) {
			String selectedText = selection.getText();
			// 处理你的翻译逻辑
			System.out.println("Selected: " + selectedText);
		}
		return null;
	}

	private ITextSelection getSelection(ExecutionEvent event) {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof ITextSelection) {
			return (ITextSelection) sel;
		}
		return null;
	}

}
