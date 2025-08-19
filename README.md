# Translation

This plugin integrates translation capabilities into the Eclipse IDE.

## Features
- Supports Baidu, Youdao, etc (requires API credentials)
- Translate selected text via context menu or keyboard shortcut
- Provide Translation View for translation
- Customize and switch translation providers from the status bar
- Lightweight and easy to configure

## Use
1. Translate selected text via context menu or keyboard shortcut  
shortcut: Ctrl + Alt + T   
![screenshot](https://raw.github.com/tlcsdm/eclipse-translation/master/plugins/com.tlcsdm.eclipse.translation/help/images/popup.png)

2. Translation View  
Click Window - Show View - Other... - Plug-in Development - Translation View  
![screenshot](https://raw.github.com/tlcsdm/eclipse-translation/master/plugins/com.tlcsdm.eclipse.translation/help/images/translate_view.png)

3. Customize and switch translation providers from the status bar  
On the right side of the eclipse status bar  
![screenshot](https://raw.github.com/tlcsdm/eclipse-translation/master/plugins/com.tlcsdm.eclipse.translation/help/images/status.png)

4. Preferences 
Click Window - Preferences - Translation  
![screenshot](https://raw.github.com/tlcsdm/eclipse-translation/master/plugins/com.tlcsdm.eclipse.translation/help/images/pref.png)

## Build

This project uses [Tycho](https://github.com/eclipse-tycho/tycho) with [Maven](https://maven.apache.org/) to build. It requires Maven 3.9.0 or higher version.

Dev build:

```
mvn clean verify
```

Release build:

```
mvn clean org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=2.0.0 verify
```

## Install

1. Add `https://raw.githubusercontent.com/tlcsdm/eclipse-translation/master/update_site/` as the upgrade location in Eclipse.
2. Download from [Jenkins](https://jenkins.tlcsdm.com/job/eclipse-plugin/job/eclipse-translation)
3. <table style="border: none;">
  <tbody>
    <tr style="border:none;">
      <td style="vertical-align: middle; padding-top: 10px; border: none;">
        <a href='http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=6999651' title='Drag and drop into a running Eclipse Indigo workspace to install eclipse-translation'> 
          <img src='https://marketplace.eclipse.org/modules/custom/eclipsefdn/eclipsefdn_marketplace/images/btn-install.svg'/>
        </a>
      </td>
      <td style="vertical-align: middle; text-align: left; border: none;">
        ← Drag it to your eclipse workbench to install! (I recommand Main Toolbar as Drop Target)
      </td>
    </tr>
  </tbody>
</table>

