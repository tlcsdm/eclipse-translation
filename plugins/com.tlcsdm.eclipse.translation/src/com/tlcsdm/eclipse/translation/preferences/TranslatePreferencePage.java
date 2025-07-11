package com.tlcsdm.eclipse.translation.preferences;

import java.net.URL;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.tlcsdm.eclipse.translation.Activator;
import com.tlcsdm.eclipse.translation.handlers.TranslateConf;
import com.tlcsdm.eclipse.translation.utils.Messages;

public class TranslatePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String APP_ID = "com.tlcsdm.eclipse.translation.baidu.APP_ID";
	public static final String SECURITY_KEY = "com.tlcsdm.eclipse.translation.baidu.SECURITY_KEY";
	public static final String TRANSLATE_PLATFORM = "com.tlcsdm.eclipse.translation.TRANSLATE_PLATFORM";
	public static final String YOUDAO_KEY = "com.tlcsdm.eclipse.translation.youdao.YOUDAO_KEY";
	public static final String YOUDAO_KEYFROM = "com.tlcsdm.eclipse.translation.youdao.YOUDAO_KEYFROM";

	public TranslatePreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.pref_desc);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = (Composite) super.createContents(parent);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);

		// 添加说明 + 链接
		Link link = new Link(composite, SWT.WRAP);
		link.setText(Messages.pref_link);
		link.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		link.addListener(SWT.Selection, e -> {
			try {
				PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(e.text));
			} catch (Exception ex) {
				MessageDialog.openError(parent.getShell(), "Failed to open link.", ex.getMessage());
			}
		});

		return composite;
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		addField(new RadioGroupFieldEditor(TRANSLATE_PLATFORM, Messages.pref_platform, 1,
				TranslateConf.TRANSLATE_PLATFORM_LIST, getFieldEditorParent()));
		addField(new PasswordFieldEditor(APP_ID, Messages.pref_baidu_id, getFieldEditorParent()));
		addField(new PasswordFieldEditor(SECURITY_KEY, Messages.pref_baidu_key, getFieldEditorParent()));
		addField(new PasswordFieldEditor(YOUDAO_KEY, Messages.pref_youdao_id, getFieldEditorParent()));
		addField(new PasswordFieldEditor(YOUDAO_KEYFROM, Messages.pref_youdao_key, getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
}
