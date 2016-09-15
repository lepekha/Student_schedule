package ruslep.student_schedule.architecture.other;

import org.androidannotations.annotations.EBean;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ruslan on 14.08.2016.
 */
@EBean
public class RxUtil {
    /**
     * {@link rx.Observable.Transformer} that transforms the source observable to subscribe in the
     * io thread and observe on the Android's UI thread.
     */
    private static Observable.Transformer ioToMainThreadSchedulerTransformer;

    static {
        ioToMainThreadSchedulerTransformer = createIOToMainThreadScheduler();
    }

    /**
     * Get {@link rx.Observable.Transformer} that transforms the source observable to subscribe in
     * the io thread and observe on the Android's UI thread.
     *
     * Because it doesn't interact with the emitted items it's safe ignore the unchecked casts.
     *
     * @return {@link rx.Observable.Transformer}
     */
    @SuppressWarnings("unchecked")
    private static <T> Observable.Transformer<T, T> createIOToMainThreadScheduler() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> applyIOToMainThreadSchedulers() {
        return ioToMainThreadSchedulerTransformer;
    }
}