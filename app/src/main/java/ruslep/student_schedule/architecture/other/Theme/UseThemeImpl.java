package ruslep.student_schedule.architecture.other.Theme;

import android.graphics.Color;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.ColorRes;

import ruslep.student_schedule.R;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferens;
import ruslep.student_schedule.architecture.model.Preferens.MyPreferensImpl;

/**
 * Created by Ruslan on 17.02.2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class UseThemeImpl implements UseTheme {

    private static final int BLUE = 0;
    private static final int RED = 1;
    private static final int GREEN = 2;
    private static final int AMBER = 3;
    private static final int PURPLE = 4;
    private static final int GREY = 5;

    @Bean(MyPreferensImpl.class)
    MyPreferens preferens;

    @Override
    public void setTheme(int themeNumber) {
        preferens.setCurrentTheme(themeNumber);
    }

    @Override
    public int getTheme() {
        switch (preferens.getCurrentTheme()){
            case BLUE:
                return R.style.AppTheme;
            case RED:
                return R.style.AppTheme_Red;
            case GREEN:
                return R.style.AppTheme_Green;
            case AMBER:
                return R.style.AppTheme_Amber;
            case PURPLE:
                return R.style.AppTheme_Purple;
            case GREY:
                return R.style.AppTheme_Gray;
            default:
                return R.style.AppTheme;
        }
    }

    @Override
    public int setNavigationBackground() {
        switch (preferens.getCurrentTheme()){
            case BLUE:
                return R.color.primary;
            case RED:
                return R.color.primary_red;
            case GREEN:
                return R.color.primary_green;
            case AMBER:
                return R.color.primary_amber;
            case PURPLE:
                return R.color.primary_purple;
            case GREY:
                return R.color.primary_grey;
            default:
                return R.color.primary;
        }
    }

    @Override
    public int getAccentColor() {
        switch (preferens.getCurrentTheme()){
            case BLUE:
                return R.color.accent;
            case RED:
                return R.color.accent_red;
            case GREEN:
                return R.color.accent_green;
            case AMBER:
                return R.color.accent_amber;
            case PURPLE:
                return R.color.accent_purple;
            case GREY:
                return R.color.accent_grey;
            default:
                return R.color.accent;
        }
    }

    @Override
    public int getDialogStyle() {
        switch (preferens.getCurrentTheme()){
            case BLUE:
                return R.style.MyAlertDialogStyle;
            case RED:
                return R.style.MyAlertDialogStyleRed;
            case GREEN:
                return R.style.MyAlertDialogStyleGreen;
            case AMBER:
                return R.style.MyAlertDialogStyleAmber;
            case PURPLE:
                return R.style.MyAlertDialogStylePurple;
            case GREY:
                return R.style.MyAlertDialogStyleGray;
            default:
                return R.style.MyAlertDialogStyle;
        }
    }
}
