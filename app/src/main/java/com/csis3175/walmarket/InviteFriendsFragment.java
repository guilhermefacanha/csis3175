package com.csis3175.walmarket;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csis3175.walmarket.database.UserDbHelper;
import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.SessionUtil;


public class InviteFriendsFragment extends Fragment {

    UserDbHelper userDbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_invite_friends, container, false);


    }

    public void confirm(View view){
        User user = SessionUtil.getUser(getActivity());
        user.setApplyFriendlyDisc(5);
        userDbHelper.updateRecord(user);

    }


}
