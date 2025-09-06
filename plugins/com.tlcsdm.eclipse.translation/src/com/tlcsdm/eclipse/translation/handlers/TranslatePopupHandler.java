package com.tlcsdm.eclipse.translation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

import com.tlcsdm.eclipse.translation.utils.TranslationUtil;

public class TranslatePopupHandler extends AbstractHandler {

	private Shell popupShell;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ITextSelection textSelection = getSelection(event);
		if (textSelection == null || textSelection.getText().isEmpty()) {
			return null;
		}

		String selectedText = textSelection.getText();
		String resultText = TranslationUtil.doTranslationAction(selectedText, TranslateConf.AUTO, TranslateConf.ZH);

		IWorkbenchPart part = HandlerUtil.getActivePart(event);
		if (!(part instanceof ITextEditor editor)) {
			return null;
		}

		StyledText styledText = (StyledText) editor.getAdapter(Control.class);
		if (styledText == null || styledText.isDisposed()) {
			return null;
		}
		int widgetOffset = -1;
		int startOffset = textSelection.getOffset();
		// 使用 ITextViewerExtension5 来做 modelOffset → widgetOffset 的转换（支持折叠）
		ITextViewer viewer = editor.getAdapter(ITextViewer.class);
		if (viewer instanceof ITextViewerExtension5 ext5) {
			widgetOffset = ext5.modelOffset2WidgetOffset(startOffset);
		} else if (viewer != null) {
			// 普通 SourceViewer，没有折叠映射，直接用
			widgetOffset = startOffset;
		}

		if (widgetOffset == -1) {
			return null; // 在折叠区域内，无法定位
		}

		// ✅ 使用 getLocationAtOffset + getLineHeight 代替 getBoundsAtOffset
		Point loc = styledText.getLocationAtOffset(widgetOffset);
		int lineHeight = styledText.getLineHeight(widgetOffset);
		Point displayLocation = styledText.toDisplay(loc.x, loc.y + lineHeight);

		// 若已有弹窗，先关闭
		if (popupShell != null && !popupShell.isDisposed()) {
			popupShell.dispose();
		}

		// 创建浮动窗口
		popupShell = new Shell(styledText.getShell(), SWT.ON_TOP | SWT.TOOL);
		popupShell.setLayout(new FillLayout());

		StyledText popupText = new StyledText(popupShell, SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		popupText.setText(resultText);
		popupText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_INFO_BACKGROUND));
		popupText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_INFO_FOREGROUND));
		popupText.setCaret(null);

		// 设置弹窗尺寸
		popupShell.pack();
		Point preferredSize = popupShell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		int maxWidth = 500, maxHeight = 300;
		int minWidth = 300, minHeight = 100;
		int width = Math.max(minWidth, Math.min(preferredSize.x, maxWidth));
		int height = Math.max(Math.min(preferredSize.y, maxHeight), minHeight);
		popupShell.setSize(width, height);

		// 防止出界
		Rectangle displayBounds = styledText.getDisplay().getBounds();
		int x = displayLocation.x;
		int y = displayLocation.y;
		if (y + height > displayBounds.height) {
			y = loc.y - height; // 显示在上方
			Point adjusted = styledText.toDisplay(loc.x, y);
			x = adjusted.x;
			y = adjusted.y;
		}
		popupShell.setLocation(x, y);
		popupShell.open();

		// 自动关闭逻辑
		Display display = popupShell.getDisplay();
		display.addFilter(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (popupShell == null || popupShell.isDisposed()) {
					display.removeFilter(SWT.MouseDown, this);
					return;
				}
				if (!(e.widget instanceof Control)) {
					return;
				}
				Control control = (Control) e.widget;
				if (!popupShell.equals(control) && !isChildOf(popupShell, control)) {
					popupShell.dispose();
					display.removeFilter(SWT.MouseDown, this);
				}
			}
		});

		return null;
	}

	// 判断控件是否为指定 shell 的子元素
	private boolean isChildOf(Shell shell, Control control) {
		while (control != null) {
			if (control == shell) {
				return true;
			}
			control = control.getParent();
		}
		return false;
	}

	private ITextSelection getSelection(ExecutionEvent event) {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof ITextSelection) {
			return (ITextSelection) sel;
		}
		return null;
	}

}
