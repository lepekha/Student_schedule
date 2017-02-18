package ruslep.student_schedule.architecture.other;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;

import java.util.Collections;

import ruslep.student_schedule.R;

/**
 * Created by Lepekha.R.O on 16.09.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Util {
    @StringRes(R.string.baseActivity_typeOfWeek_Cheslitel)
    String CHESLITEL;

    @StringRes(R.string.baseActivity_typeOfWeek_Znamenatel)
    String ZNAMENATEL;

    @StringRes(R.string.baseActivity_typeOfWeek_AllWeek)
    String ALL_WEEK;

    public int getCheslitel(){
        return 0;
    }
}
