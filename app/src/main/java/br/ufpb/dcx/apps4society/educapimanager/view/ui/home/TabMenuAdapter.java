package br.ufpb.dcx.apps4society.educapimanager.view.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import br.ufpb.dcx.apps4society.educapimanager.view.ui.challenge.ChallengeFragment;
import br.ufpb.dcx.apps4society.educapimanager.view.ui.context.ContextFragment;


public class TabMenuAdapter extends FragmentPagerAdapter {

    private Integer size = 2;
    private Fragment currentFragemnt;

    public TabMenuAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                currentFragemnt = new ContextFragment();
                break;
            case 1:
                currentFragemnt = new ChallengeFragment();
                break;
            default:
                currentFragemnt = new ContextFragment();
                break;
        }

        return currentFragemnt;
    }

    @Override
    public int getCount() {
        return this.size;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Contextos";
            case 1:
                return "Desafios";
            default:
                return "Contextos";
        }
    }

    public Fragment getCurrentFragment(){
        return currentFragemnt;
    }
}
