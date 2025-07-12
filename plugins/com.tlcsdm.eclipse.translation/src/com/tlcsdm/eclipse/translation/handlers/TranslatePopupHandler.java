package com.tlcsdm.eclipse.translation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
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
		if (textSelection != null) {
			String selectedText = textSelection.getText();
			if (selectedText == null || selectedText.isEmpty()) {
				return null;
			}

			String resultText = TranslationUtil.doTranslationAction(selectedText, TranslateConf.AUTO, TranslateConf.ZH);

			// 获取当前编辑器控件以定位弹窗位置
			IWorkbenchPart part = HandlerUtil.getActivePart(event);
			if (!(part instanceof ITextEditor editor)) {
				return null;
			}

			StyledText styledText = (StyledText) editor.getAdapter(Control.class);
			if (styledText == null || styledText.isDisposed()) {
				return null;
			}

			// 获取文本区域位置，计算弹窗坐标
			Point location = styledText.getLocationAtOffset(textSelection.getOffset());
			Point displayLocation = styledText.toControl(location);

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
			// 不显示光标
			popupText.setCaret(null);

			// 设置弹窗尺寸
			int width = 300;
			int height = 100;
			popupShell.setSize(width, height);
			popupShell.setLocation(displayLocation.x, displayLocation.y + 20); // 显示在选中文本下方
			popupShell.open();

			// 自动关闭逻辑：点击窗口外关闭
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

		}
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
