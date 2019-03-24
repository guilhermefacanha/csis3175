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
    EditText  txtFirstName1,txtFirstName2,txtFirstName3,txtFirstName4,txtFirstName5;
    EditText txtFriendEmail1,txtFriendEmail2,txtFriendEmail3,txtFriendEmail4,txtFriendEmail5;
    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


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

        txtFirstName1 = mainActivity.findViewById(R.id.txtFirstName_1);
        txtFirstName2 = mainActivity.findViewById(R.id.txtFirstName_2);
        txtFirstName3 = mainActivity.findViewById(R.id.txtFirstName_3);
        txtFirstName4 = mainActivity.findViewById(R.id.txtFirstName_4);
        txtFirstName5 = mainActivity.findViewById(R.id.txtFirstName_5);

        txtFriendEmail1 = mainActivity.findViewById(R.id.txtFriendEmail_1);
        txtFriendEmail2 = mainActivity.findViewById(R.id.txtFriendEmail_2);
        txtFriendEmail3 = mainActivity.findViewById(R.id.txtFriendEmail_3);
        txtFriendEmail4 = mainActivity.findViewById(R.id.txtFriendEmail_4);
        txtFriendEmail5 = mainActivity.findViewById(R.id.txtFriendEmail_5);

        btnInviteFriends = mainActivity.findViewById(R.id.btnInviteFriends);

        btnInviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                     if (txtFirstName1.getText().toString().isEmpty() || txtFriendEmail1.getText().toString().isEmpty()) {
                      //MessageUtil.addMessage("Invite at least 1 friend to get 10% OFF", mainActivity);
                         throw new Exception("Invite at least 1 friend to get 10% OFF");
                     }

                     if (!txtFriendEmail1.getText().toString().matches(emailPattern))
                        throw new Exception("Enter a valid Email address");

                     if (!txtFriendEmail2.getText().toString().matches(emailPattern) && !txtFriendEmail2.getText().toString().isEmpty())
                        throw new Exception("Enter a valid Email address");
                     if (!txtFriendEmail3.getText().toString().matches(emailPattern)&& !txtFriendEmail3.getText().toString().isEmpty())
                        throw new Exception("Enter a valid Email address");
                     if (!txtFriendEmail4.getText().toString().matches(emailPattern)&& !txtFriendEmail4.getText().toString().isEmpty())
                        throw new Exception("Enter a valid Email address");
                     if (!txtFriendEmail5.getText().toString().matches(emailPattern)&& !txtFriendEmail5.getText().toString().isEmpty())
                        throw new Exception("Enter a valid Email address");

                     else {
                        confirmFriendInvitation(v);
                        MessageUtil.addMessage("You Got 10% OFF in your next purchase", mainActivity);
                    }
                }
                catch (Exception e) {
                            MessageUtil.addMessage(e.getMessage(), mainActivity);
                }
            }
        });


    }

    public void confirmFriendInvitation(View view){
        User user = userDbHelper.getUserByEmail(SessionUtil.getUser(getActivity()).getEmail());
        user.setApplyFriendlyDisc(10);
        userDbHelper.updateRecord(user);

    }


}
