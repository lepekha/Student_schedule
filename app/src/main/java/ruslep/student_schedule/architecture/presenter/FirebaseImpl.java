package ruslep.student_schedule.architecture.presenter;

import ruslep.student_schedule.architecture.view.BaseActivity;

/**
 * Created by Ruslan on 21.08.2017.
 */

public class FirebaseImpl implements Firebase {
    private BaseActivity view;

    @Override
    public void setView(BaseActivity view) {
        this.view = view;
    }

    @Override
    public void auth() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void delete() {

    }
}
