package com.tlcsdm.eclipse.translation.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tlcsdm.eclipse.translation.Activator;
import com.tlcsdm.eclipse.translation.handlers.TranslateConf;

public class TranslatePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String APP_ID = "com.tlcsdm.eclipse.translation.baidu.APP_ID";
	public static final String SECURITY_KEY = "com.tlcsdm.eclipse.translation.baidu.SECURITY_KEY";
	public static final String TRANSLATE_PLATFORM = "com.tlcsdm.eclipse.translation.TRANSLATE_PLATFORM";
	public static final String YOUDAO_KEY = "com.tlcsdm.eclipse.translation.youdao.YOUDAO_KEY";
	public static final String YOUDAO_KEYFROM = "com.tlcsdm.eclipse.translation.youdao.YOUDAO_KEYFROM";

	public TranslatePreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("请填写需要使用的翻译平台相关的验证信息。");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		addField(new RadioGroupFieldEditor(TRANSLATE_PLATFORM, "选择翻译平台，请注意你选择的平台必须填写授权的id和key，若勾选全部，请全部填写。", 1,
				TranslateConf.TRANSLATE_PLATFORM_LIST, getFieldEditorParent()));
		addField(new StringFieldEditor(APP_ID, "百度翻译的APP_ID:", getFieldEditorParent()));
		addField(new StringFieldEditor(SECURITY_KEY, "百度翻译的SECURITY_KEY:", getFieldEditorParent()));
		addField(new StringFieldEditor(YOUDAO_KEY, "有道翻译的KEY:", getFieldEditorParent()));
		addField(new StringFieldEditor(YOUDAO_KEYFROM, "有道翻译的KEYFROM:", getFieldEditorParent()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
}
