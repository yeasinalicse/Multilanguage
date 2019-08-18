package com.yeasin.multilanguage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;


import java.util.Locale;


public class MultiLanguageUtil {

    private static final String TAG = "MultiLanguageUtil";
    private static MultiLanguageUtil instance;
    private Context mContext;
    public static final String SAVE_LANGUAGE = "save_language";

    public static void init(Context mContext) {
        if (instance == null) {
            synchronized (MultiLanguageUtil.class) {
                if (instance == null) {
                    instance = new MultiLanguageUtil(mContext);
                }
            }
        }
    }

    public static MultiLanguageUtil getInstance() {
        if (instance == null) {
            throw new IllegalStateException("You must be init MultiLanguageUtil first");
        }
        return instance;
    }

    private MultiLanguageUtil(Context context) {
        this.mContext = context;
    }

    /**
     * 设置语言
     */
    public void setConfiguration(Activity activity) {
        Locale targetLocale = getLanguageLocale();
        Configuration configuration = activity.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(targetLocale);
        } else {
            configuration.locale = targetLocale;
        }
        Resources resources = activity.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
    }


    private Locale getLanguageLocale() {
        int languageType = PreferenceUtils.getInt(mContext, MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_EN);
        if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            return Locale.ENGLISH;
        } else if (languageType == LanguageType.LANGUAGE_EN) {
            return Locale.ENGLISH;
//            return Locale.ENGLISH;
        } else if (languageType == LanguageType.LANGUAGE_BN) {
            Locale locale = new Locale("bn");
//            Locale.setDefault(locale);
            return locale;
//            return Locale.ENGLISH;
        } else if (languageType == LanguageType.LANGUAGE_CHINESE) {
            return Locale.TRADITIONAL_CHINESE;
        }
        Log.e("TAG","getLanguageLocale" + languageType);
        getSystemLanguage(getSysLocale());
        Log.e(TAG, "getLanguageLocale" + languageType + languageType);
        return Locale.SIMPLIFIED_CHINESE;
    }

    private String getSystemLanguage(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();

    }

    //以上获取方式需要特殊处理一下
    public Locale getSysLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * 更新语言
     *
     * @param languageType
     */
    public void updateLanguage(Activity activity,int languageType) {
        PreferenceUtils.putInt(mContext, MultiLanguageUtil.SAVE_LANGUAGE, languageType);
        MultiLanguageUtil.getInstance().setConfiguration(activity);
    }

    public String getLanguageName(Context context) {
        int languageType = PreferenceUtils.getInt(mContext, MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_FOLLOW_SYSTEM);
        if (languageType == LanguageType.LANGUAGE_EN) {
            return mContext.getString(R.string.setting_language_english);
        } else if (languageType == LanguageType.LANGUAGE_BN) {
            return mContext.getString(R.string.setting_bn);
        } else if (languageType == LanguageType.LANGUAGE_CHINESE) {
            return mContext.getString(R.string.setting_traditional_chinese);
        }
        return mContext.getString(R.string.setting_language_auto);
    }

    /**
     * 获取到用户保存的语言类型
     *
     * @return
     */
    public int getLanguageType() {
        int languageType = PreferenceUtils.getInt(mContext, MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_FOLLOW_SYSTEM);
        if (languageType == LanguageType.LANGUAGE_BN) {
            return LanguageType.LANGUAGE_BN;
        } else if (languageType == LanguageType.LANGUAGE_CHINESE) {
            return LanguageType.LANGUAGE_CHINESE;
        }else if(languageType == LanguageType.LANGUAGE_EN) {
            return LanguageType.LANGUAGE_EN;
        }
        else if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            return LanguageType.LANGUAGE_FOLLOW_SYSTEM;
        }
        return languageType;
    }

    public static Context attachBaseContext(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context);
        } else {
            MultiLanguageUtil.getInstance().setConfiguration(context);
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getInstance().getLanguageLocale();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }


}
