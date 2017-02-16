package ruslep.student_schedule.architecture.view;

import android.content.Context;
import android.support.v4.view.ViewPager;

import org.androidannotations.annotations.EBean;

/**
 * Created by Ruslan on 11.08.2016.
 */
public interface BaseActivity extends View {
    void hideAuthBtn();
    void showMessage(String text);
    void setTextTypeOfWeek(String typeOfWeek);
    void showProgressBar();
    void hideProgressBar();
    void setDrawerHeaderPhoneNumber(String phoneNumber);
    String getTextTypeOfWeek();
    void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults);
    boolean checkPermissions();
}
