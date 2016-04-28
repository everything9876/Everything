package com.everything.fragments.hydrantFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.everything.R;
import com.everything.fragments.BaseFragment;


/**
 * Created by Mirek on 2016-03-18.
 */
public class ISPrincipleFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.fragmentLayout = R.layout.int_segr_princ_fragment;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

//////////////DO NOT DO THAT:

    class ClassWhereUserNeedsToImplementOnlyJmj1Method implements Common {

        @Override
        public void doSomething() {

        }

        @Override
        public void doSomethingMore() {

        }

        @Override
        public void doSomethingElse() {

        }

        @Override
        public void doSomethingMoreAndMore() {

        }

        @Override
        public void jmj1() {

        }

        @Override
        public void jmj2() {

        }
    }

    class ClassWhereUserNeedsToImplementOnlyJmj2Method implements Common {

        @Override
        public void doSomething() {

        }

        @Override
        public void doSomethingMore() {

        }

        @Override
        public void doSomethingElse() {

        }

        @Override
        public void doSomethingMoreAndMore() {

        }

        @Override
        public void jmj1() {

        }

        @Override
        public void jmj2() {

        }
    }

    public interface Common {
        void doSomething();

        void doSomethingMore();

        void doSomethingElse();

        void doSomethingMoreAndMore();

        void jmj1();

        void jmj2();
    }

//THIS WAY IS CORRECT:

    class CorrectClassWhereUserNeedsToImplementOnlyJmj1Method implements Jmj1{
        @Override
        public void jmj1() {

        }
    }

    class CorrectClassWhereUserNeedsToImplementOnlyJmj2Method implements Jmj2{
        @Override
        public void jmj2() {

        }
    }

    public interface DoSomething{
        void doSomething();
    }

    public interface DoSomethingMore{
        void doSomethingMore();
    }

    public interface DoSomethingElse{
        void doSomethingElse();
    }

    public interface DoSomethingMoreAndMore{
        void doSomethingMoreAndMore();
    }

    public interface Jmj1{
        void jmj1();
    }

    public interface Jmj2{
        void jmj2();
    }

}





