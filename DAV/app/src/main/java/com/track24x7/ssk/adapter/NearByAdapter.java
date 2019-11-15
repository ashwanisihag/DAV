package com.davians.earth.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.text.TextUtils;
import com.davians.earth.R;
import com.davians.earth.activity.UserDetailActivity;
import com.davians.earth.pojo.NearByPOJO;
import com.davians.earth.pojo.UserListPOJO;
import com.davians.earth.util.Pref;
import com.davians.earth.util.StringUtils;
import com.davians.earth.util.UtilFunction;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ashwani Sihag on 03-11-2017.
 */

public class NearByAdapter extends RecyclerView.Adapter<NearByAdapter.ViewHolder> {
    private List<NearByPOJO> items;
    Activity activity;
    Fragment fragment;

    public NearByAdapter(Activity activity, Fragment fragment, List<NearByPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_near_by_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String schoolName=items.get(position).getSchool();
        if(schoolName.contains("Thermal"))
        {
            Drawable img = ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.common_full_open_on_phone);
            img.setBounds(0, 0, 150, 150);
            holder.inf_nearby_institute.setCompoundDrawables(null, img, null, null);

        }

        holder.inf_nearby_institute.setText("School :- " +items.get(position).getSchool());
        holder.inf_nearby_user_name.setText("Name :- " +items.get(position).getFirstName() + " " + items.get(position).getLastName());

        if (items.get(position).getCity() != null && !TextUtils.isEmpty(items.get(position).getCity())) {
            holder.inf_nearby_city.setText("City :- " + items.get(position).getCity());
        }
        else {
            holder.inf_nearby_city.setText("City :- Not Specified");
        }

        if (items.get(position).getPosting() != null && !TextUtils.isEmpty(items.get(position).getPosting())) {
            holder.inf_nearby_posting.setText("Posted at :- " + items.get(position).getPosting());
        }
        else {
            holder.inf_nearby_posting.setText("Posted at :- Not Specified");
        }

        holder.inf_nearby_RollNo.setText("Roll No :- "+ items.get(position).getRollNo().toString());

        if (items.get(position).getDepartment() != null && !TextUtils.isEmpty(items.get(position).getDepartment())) {
            holder.inf_nearby_department.setText("Department :- " + items.get(position).getDepartment());
        }
        else {
            holder.inf_nearby_department.setText("Department :- Not Specified");
        }
        if (items.get(position).getProfession() != null && !TextUtils.isEmpty(items.get(position).getProfession())) {
            holder.inf_nearby_profession.setText("Profession :- " + items.get(position).getProfession());
        }
        else {
            holder.inf_nearby_profession.setText("Profession :- Not Specified");
        }
        if (items.get(position).getDesignation() != null && !TextUtils.isEmpty(items.get(position).getDesignation())) {
            holder.inf_nearby_designation.setText("Designation :- " + items.get(position).getDesignation());
        }
        else {
            holder.inf_nearby_designation.setText("Designation :- Not Specified");
        }

        //holder.inf_nearby_phoneNumber.setText("Phone :- "+ items.get(position).getPhoneNumber());

        holder.inf_nearby_joining_year.setText("Joined in :- "+ UtilFunction.getYear(items.get(position).getJoiningYear()));
        try {
            double distance=items.get(position).getDistance()/1000;
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            holder.inf_nearby_distance.setText("Distance  :- " + df.format(distance)+" km");
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.inf_nearby_days_ago.setText("Location updated :- "+ items.get(position).getDaysAgo());

        holder.ll_nearby_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, UserDetailActivity.class);
                intent.putExtra("userID",items.get(position).getId());
                activity.startActivity(intent);
            }
        });

        if(Integer.parseInt(Pref.GetStringPref(holder.itemView.getContext(), StringUtils.Batch, ""))>Integer.parseInt(UtilFunction.getYear(items.get(position).getLeavingYear())))
        {
            holder.user_rank.setText("Big Brother");
        }
        else
        {
            holder.user_rank.setText("Younger Brother");
        }
        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.inf_nearby_institute)
        TextView inf_nearby_institute;
        @BindView(R.id.inf_nearby_user_name)
        TextView inf_nearby_user_name;
       @BindView(R.id.inf_nearby_joining_year)
        TextView inf_nearby_joining_year;
        //@BindView(R.id.inf_nearby_phone_number)
        //TextView inf_nearby_phoneNumber;
        @BindView(R.id.inf_nearby_days_ago)
        TextView inf_nearby_days_ago;
        @BindView(R.id.inf_nearby_designation)
        TextView inf_nearby_designation;
        @BindView(R.id.inf_nearby_department)
        TextView inf_nearby_department;
        @BindView(R.id.inf_nearby_profession)
        TextView inf_nearby_profession;
        @BindView(R.id.inf_nearby_RollNo)
        TextView inf_nearby_RollNo;
        @BindView(R.id.inf_nearby_city)
        TextView inf_nearby_city;
        @BindView(R.id.inf_nearby_posting)
        TextView inf_nearby_posting;
        @BindView(R.id.inf_nearby_distance)
        TextView inf_nearby_distance;
        @BindView(R.id.ll_nearby_user)
        LinearLayout ll_nearby_user;
        @BindView(R.id.ll_back)
        LinearLayout ll_back;
        @BindView(R.id.user_rank)
        TextView user_rank;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
