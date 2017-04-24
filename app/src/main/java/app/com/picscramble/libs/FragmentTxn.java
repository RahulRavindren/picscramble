package app.com.picscramble.libs;


import app.com.picscramble.MainActivity;
import app.com.picscramble.fragments.BaseFragment;

public final class FragmentTxn {
    private static MainActivity activity;

    public FragmentTxn(MainActivity activity) {
        this.activity = activity;
    }

    public static class FragmentPush extends FragmentTxnType {
        BaseFragment fragment;

        public FragmentPush(BaseFragment fragment) {
            this.fragment = fragment;
        }

        @Override
        protected void performTransaction() {
            activity.addFragment(fragment);
        }
    }

    public static class FragmentAddWithoutBackStack extends FragmentTxnType {
        BaseFragment fragment;

        public FragmentAddWithoutBackStack(BaseFragment fragment) {
            this.fragment = fragment;
        }

        @Override
        protected void performTransaction() {
            activity.addFragmentWithoutBackStack(fragment);
        }
    }

    public static class FragmentPop extends FragmentTxnType {

        @Override
        protected void performTransaction() {
            activity.pop();
        }
    }

    public static class FragmentPopAll extends FragmentTxnType {

        @Override
        protected void performTransaction() {
            activity.popAll();
        }
    }

    public static class FragmentPopUntil extends FragmentTxnType {
        String tag;

        public FragmentPopUntil(String tag) {
            this.tag = tag;
        }

        @Override
        protected void performTransaction() {
            activity.popUntil(tag);
        }
    }
}
