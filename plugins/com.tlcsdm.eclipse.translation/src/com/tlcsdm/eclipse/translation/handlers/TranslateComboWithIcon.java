package com.tlcsdm.eclipse.translation.handlers;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import com.tlcsdm.eclipse.translation.Activator;
import com.tlcsdm.eclipse.translation.preferences.TranslatePreferencePage;

public class TranslateComboWithIcon extends WorkbenchWindowControlContribution {

	private Image baiduIcon;
	private Image tencentIcon;
	private Image youdaoIcon;
	private Label iconLabel;

	public TranslateComboWithIcon() {
		super("com.tlcsdm.eclipse.translation.status.combo");
		baiduIcon = Activator.getImageDescriptor("icons/baidu.png").createImage();
		youdaoIcon = Activator.getImageDescriptor("icons/youdao.png").createImage();
		tencentIcon = Activator.getImageDescriptor("icons/tencent.png").createImage();
	}

	@Override
	protected Control createControl(Composite parent) {
		parent.getParent().setRedraw(true);
		Composite container = new Composite(parent, SWT.NONE);

		GridLayout glContainer = new GridLayout(2, false);
		glContainer.marginTop = 0;
		glContainer.marginBottom = 0;
		glContainer.marginHeight = 0;
		glContainer.marginWidth = 0;
		glContainer.verticalSpacing = 0;
		container.setLayout(glContainer);

		iconLabel = new Label(container, SWT.NONE);
		iconLabel.setImage(baiduIcon);
		GridData iconData = new GridData(SWT.CENTER, SWT.FILL, false, false);
		iconLabel.setLayoutData(iconData);

		GridData glReader = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		glReader.widthHint = 120;
		glReader.heightHint = 20;
		CCombo combo = new CCombo(container, SWT.FLAT | SWT.READ_ONLY);
		combo.setLayoutData(glReader);

		String[][] listStrings = TranslateConf.TRANSLATE_PLATFORM_LIST;
		for (String[] itemData : listStrings) {
			combo.add(itemData[0]);
		}
		String[] items = combo.getItems();

		// 读取上次选择
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.addPropertyChangeListener(event -> {
			if (TranslatePreferencePage.TRANSLATE_PLATFORM.equals(event.getProperty())) {
				String savedValue = store.getString(TranslatePreferencePage.TRANSLATE_PLATFORM);
				int defaultIndex = 0;
				for (int i = 0; i < items.length; i++) {
					if (getKey(i).equals(savedValue)) {
						defaultIndex = i;
						break;
					}
				}
				combo.select(defaultIndex);
				updateIconByKey(getKey(defaultIndex));
			}
		});
		String savedValue = store.getString(TranslatePreferencePage.TRANSLATE_PLATFORM);
		int defaultIndex = 0;
		for (int i = 0; i < items.length; i++) {
			if (getKey(i).equals(savedValue)) {
				defaultIndex = i;
				break;
			}
		}

		combo.addListener(SWT.Selection, e -> {
			int idx = combo.getSelectionIndex();
			if (idx < 0 || idx >= TranslateConf.TRANSLATE_PLATFORM_LIST.length) {
				return;
			}
			String key = TranslateConf.TRANSLATE_PLATFORM_LIST[idx][1]; // 获取第二个值 key
			store.setValue(TranslatePreferencePage.TRANSLATE_PLATFORM, getKey(idx));
			updateIconByKey(key);
		});
		combo.select(defaultIndex);
		updateIconByKey(getKey(defaultIndex));

		return container;
	}

	private String getKey(int index) {
		if (index >= 0) {
			String key = TranslateConf.TRANSLATE_PLATFORM_LIST[index][1];
			return key;
		}
		return "";
	}

	private void updateIconByKey(String key) {
		if ("baidu".equals(key)) {
			iconLabel.setImage(baiduIcon);
		} else if ("youdao".equals(key)) {
			iconLabel.setImage(youdaoIcon);
		} else if ("tencent".equals(key)) {
			iconLabel.setImage(tencentIcon);
		} else {
			iconLabel.setImage(null);
		}
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public void dispose() {
		if (baiduIcon != null && !baiduIcon.isDisposed()) {
			baiduIcon.dispose();
		}
		if (youdaoIcon != null && !youdaoIcon.isDisposed()) {
			youdaoIcon.dispose();
		}
		if (tencentIcon != null && !tencentIcon.isDisposed()) {
			tencentIcon.dispose();
		}
		super.dispose();
	}
}