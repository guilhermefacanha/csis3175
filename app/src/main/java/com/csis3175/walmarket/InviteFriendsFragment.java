package com.csis3175.walmarket;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.csis3175.walmarket.database.StoreDbHelper;
import com.csis3175.walmarket.database.UserDbHelper;
import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.MessageUtil;
import com.csis3175.walmarket.util.SessionUtil;


public class InviteFriendsFragment extends Fragment {

    UserDbHelper userDbHelper;
    MainActivity mainActivity;
    Button btnInviteFriends;
    EditText  txtFirstFriend1,txtFirstFriend2,txtFirstFriend3,txtFirstFriend4,txtFirstFriend5;
    EditText txtFriendEmail1,txtFriendEmail2,txtFriendEmail3,txtFriendEmail4,txtFriendEmail5;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invite_friends, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        userDbHelper = new UserDbHelper(mainActivity);

        txtFirstFriend1 = mainActivity.findViewById(R.id.txtFirstName_1);
        txtFirstFriend2 = mainActivity.findViewById(R.id.txtFirstName_2);
        txtFirstFriend3 = mainActivity.findViewById(R.id.txtFirstName_3);
        txtFirstFriend4 = mainActivity.findViewById(R.id.txtFirstName_4);
        txtFirstFriend5 = mainActivity.findViewById(R.id.txtFirstName_5);

        txtFriendEmail1 = mainActivity.findViewById(R.id.txtFriendEmail_1);
        txtFriendEmail2 = mainActivity.findViewById(R.id.txtFriendEmail_2);
        txtFriendEmail3 = mainActivity.findViewById(R.id.txtFriendEmail_3);
        txtFriendEmail4 = mainActivity.findViewById(R.id.txtFriendEmail_4);
        txtFriendEmail5 = mainActivity.findViewById(R.id.txtFriendEmail_5);

        btnInviteFriends = mainActivity.findViewById(R.id.btnInviteFriends);

        btnInviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtFirstFriend1 != null && txtFriendEmail1 !=null){
                    confirm(v);
                    MessageUtil.addMessage("You Got 10% OFF in your next purchase", mainActivity);
                }

            }
        });


    }

    public void confirm(View view){
        User user = SessionUtil.getUser(getActivity());
        user.setApplyFriendlyDisc(10);
        userDbHelper.updateRecord(user);

    }


}
