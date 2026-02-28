package com.tlcsdm.eclipse.translation.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.tlcsdm.eclipse.translation.Activator;
import com.tlcsdm.eclipse.translation.handlers.TranslateConf;
import com.tlcsdm.eclipse.translation.preferences.TranslatePreferencePage;
import com.tlcsdm.eclipse.translation.utils.Messages;
import com.tlcsdm.eclipse.translation.utils.TranslationUtil;

public class TranslationView extends ViewPart implements ModifyListener {

	public static final String ID = "com.tlcsdm.eclipse.translation.views.TranslationView";
	private Button btnCheckAuto;
	private Text textQuery, textResult;
	private Combo cboTranslationModelFrom, cboTranslationModelTo;
	private IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();

	private final static List<Map<String, String>> mTranslationModelDataSource = new ArrayList<>();

	static {
		mTranslationModelDataSource.add(Map.of(TranslateConf.AUTO, Messages.translateConf_auto));
		mTranslationModelDataSource.add(Map.of(TranslateConf.ZH, "中文"));
		mTranslationModelDataSource.add(Map.of(TranslateConf.JA, "日本語"));
		mTranslationModelDataSource.add(Map.of(TranslateConf.EN, "English"));
	}

	public TranslationView() {
		super();
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(4, true));

		cboTranslationModelFrom = new Combo(parent, SWT.READ_ONLY);
		cboTranslationModelFrom.addModifyListener(this);
		cboTranslationModelFrom.setToolTipText(Messages.view_from);
		cboTranslationModelFrom.setItems(getTranslationModelItems());
		cboTranslationModelFrom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		cboTranslationModelFrom.select(0);

		cboTranslationModelTo = new Combo(parent, SWT.READ_ONLY);
		cboTranslationModelTo.addModifyListener(this);
		cboTranslationModelTo.setToolTipText(Messages.view_to);
		cboTranslationModelTo.setItems(getTranslationModelItems());
		cboTranslationModelTo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		cboTranslationModelTo.select(1);

		Button btnTranslation = new Button(parent, SWT.FLAT);
		btnTranslation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				btnTranslation.getDisplay().asyncExec(() -> doTranslationAction());
			}
		});
		btnTranslation.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		btnTranslation.setText(Messages.view_btn_translate);

		btnCheckAuto = new Button(parent, SWT.CHECK);
		btnCheckAuto.setSelection(true);
		btnCheckAuto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				preferenceStore.setValue(TranslatePreferencePage.TRANSLATE_PLATFORM, btnCheckAuto.getSelection());
			}
		});
		btnCheckAuto.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false, 1, 1));
		btnCheckAuto.setText(Messages.view_auto_translate);

		textQuery = new Text(parent, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textQuery.addModifyListener(this);
		GridData gd_textQuery = new GridData(GridData.FILL_BOTH);
		gd_textQuery.horizontalSpan = 2;
		gd_textQuery.widthHint = 464;
		textQuery.setLayoutData(gd_textQuery);

		textResult = new Text(parent, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textResult.setEditable(false);
		GridData gd_textResult = new GridData(GridData.FILL_BOTH);
		gd_textResult.grabExcessHorizontalSpace = false;
		gd_textResult.horizontalSpan = 2;
		textResult.setLayoutData(gd_textResult);
	}

	private void doTranslationAction() {
		String query = textQuery.getText().trim();
		String from = getTranslationMode(cboTranslationModelFrom.getText());
		String to = getTranslationMode(cboTranslationModelTo.getText());
		String translateResult = TranslationUtil.doTranslationAction(query, from, to);
		textResult.setText(translateResult);
	}

	private static String[] mTranslationModelItems = null;

	private String[] getTranslationModelItems() {
		if (null == mTranslationModelItems) {
			List<String> textList = new ArrayList<>();
			mTranslationModelDataSource.forEach((map) -> {
				map.keySet().forEach((key) -> {
					textList.add(map.get(key));
				});
			});
			mTranslationModelItems = textList.toArray(new String[textList.size()]);
		}
		return mTranslationModelItems;
	}

	private String getTranslationMode(String text) {
		for (Map<String, String> map : mTranslationModelDataSource) {
			for (String key : map.keySet()) {
				if (map.get(key).equals(text)) {
					return key;
				}
			}
		}
		return TranslateConf.AUTO;
	}

	@Override
	public void modifyText(ModifyEvent arg0) {
		if (null != btnCheckAuto && btnCheckAuto.getSelection()) {
			btnCheckAuto.getDisplay().asyncExec(() -> doTranslationAction());
		}
	}

	@Override
	public void setFocus() {
		// Do nothing
	}

}
